package br.com.challenge.desafiosangiorgio.service;

import br.com.challenge.desafiosangiorgio.model.Pagamento;
import br.com.challenge.desafiosangiorgio.model.dto.PagamentoDto;
import br.com.challenge.desafiosangiorgio.repository.CobrancaRepository;
import br.com.challenge.desafiosangiorgio.exception.CobrancaNaoEncontradaException;
import br.com.challenge.desafiosangiorgio.model.Cobranca;
import br.com.challenge.desafiosangiorgio.model.enums.StatusPagamento;
import br.com.challenge.desafiosangiorgio.repository.PagamentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
public class PagamentoService {

    @Autowired
    private CobrancaRepository cobrancaRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    private final ObjectMapper objectMapper;

    public PagamentoService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public StatusPagamento verificarStatusPagamento(BigDecimal valorPago, UUID codigoCobranca) {
        Cobranca cobranca = cobrancaRepository.findById(codigoCobranca).orElseThrow(() -> new CobrancaNaoEncontradaException("Código da cobrança não foi encontrado."));
        BigDecimal valorOriginal = cobranca.getValor();

        if (valorPago.compareTo(valorOriginal) < 0) {
            return StatusPagamento.PARCIAL;
        } else if (valorPago.compareTo(valorOriginal) == 0) {
            return StatusPagamento.TOTAL;
        } else {
            return StatusPagamento.EXCEDENTE;
        }
    }

    public void salvarPagamento(PagamentoDto pagamentoDto) {
        Pagamento pagamento = objectMapper.convertValue(pagamentoDto, Pagamento.class);
        pagamentoRepository.save(pagamento);
    }
}
