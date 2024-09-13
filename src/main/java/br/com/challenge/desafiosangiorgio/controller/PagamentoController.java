package br.com.challenge.desafiosangiorgio.controller;

import br.com.challenge.desafiosangiorgio.model.dto.PagamentoDto;
import br.com.challenge.desafiosangiorgio.model.dto.RequestDto;
import br.com.challenge.desafiosangiorgio.model.enums.StatusPagamento;
import br.com.challenge.desafiosangiorgio.service.PagamentoService;
import br.com.challenge.desafiosangiorgio.service.SQSService;
import br.com.challenge.desafiosangiorgio.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private SQSService sqsService;

    @PostMapping
    public ResponseEntity<RequestDto> processarPagamentos(@RequestBody RequestDto requisicao) {
        validatorService.validarVendedor(requisicao.codigo_vendedor());

        for (PagamentoDto pagamento : requisicao.pagamentoList()) {
            validatorService.validarCobranca(pagamento.getCodigoCobranca());
            StatusPagamento status = pagamentoService.verificarStatusPagamento(pagamento.getValor(), pagamento.getCodigoCobranca());
            pagamento.setStatus(status);
            pagamentoService.salvarPagamento(pagamento);
            sqsService.enviarParaFila(requisicao, status);
        }

        return ResponseEntity.ok(requisicao);
    }
}

