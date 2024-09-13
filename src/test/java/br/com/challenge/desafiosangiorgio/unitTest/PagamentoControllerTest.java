package br.com.challenge.desafiosangiorgio.unitTest;

import br.com.challenge.desafiosangiorgio.controller.PagamentoController;
import br.com.challenge.desafiosangiorgio.model.dto.PagamentoDto;
import br.com.challenge.desafiosangiorgio.model.dto.RequestDto;
import br.com.challenge.desafiosangiorgio.model.enums.StatusPagamento;
import br.com.challenge.desafiosangiorgio.service.PagamentoService;
import br.com.challenge.desafiosangiorgio.service.SQSService;
import br.com.challenge.desafiosangiorgio.service.ValidatorService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class PagamentoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PagamentoController pagamentoController;

    @MockBean
    private ValidatorService validacaoService;

    @MockBean
    private PagamentoService pagamentoService;

    @MockBean
    private SQSService sqsService;


    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testProcessarPagamentos() throws Exception {
        // Arrange
        UUID vendedorId = UUID.randomUUID();
        UUID cobrancaId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.valueOf(100.00);

        PagamentoDto pagamentoDto = new PagamentoDto(cobrancaId, valor, null);
        RequestDto requestDto = new RequestDto(vendedorId, List.of(pagamentoDto));

        doNothing().when(validacaoService).validarVendedor(vendedorId);
        doNothing().when(validacaoService).validarCobranca(cobrancaId);
        when(pagamentoService.verificarStatusPagamento(valor, cobrancaId)).thenReturn(StatusPagamento.PARCIAL);
        doNothing().when(sqsService).enviarParaFila(requestDto, StatusPagamento.PARCIAL);

        mockMvc.perform(post("/pagamentos") // Altere o endpoint conforme necessário
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo_vendedor").value(vendedorId.toString()))
                .andExpect(jsonPath("$.pagamento_list[0].codigo_cobranca").value(cobrancaId.toString()))
                .andExpect(jsonPath("$.pagamento_list[0].valor").value(100.00))
                .andExpect(jsonPath("$.pagamento_list[0].status").value("PARCIAL"));
    }
}
