package br.com.challenge.desafiosangiorgio.integrationTest;

import br.com.challenge.desafiosangiorgio.model.dto.PagamentoDto;
import br.com.challenge.desafiosangiorgio.model.dto.RequestDto;
import br.com.challenge.desafiosangiorgio.repository.CobrancaRepository;
import br.com.challenge.desafiosangiorgio.repository.VendedorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PagamentoControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private CobrancaRepository cobrancaRepository;

    private static final String CODIGO_VENDEDOR = "e154ddc7-48a8-4336-9199-73a56137e220";
    private static final String CODIGO_COBRANCA = "a1e724f9-f98f-43ad-96b6-3d4c1256b164";
    private static final BigDecimal VALOR = new BigDecimal("100.00");


    @Test
    public void testProcessarPagamentos() throws Exception {
        assertTrue(vendedorRepository.findById(UUID.fromString(CODIGO_VENDEDOR)).isPresent());
        assertTrue(cobrancaRepository.findById(UUID.fromString(CODIGO_COBRANCA)).isPresent());
        PagamentoDto pagamentoDto = new PagamentoDto(UUID.fromString(CODIGO_COBRANCA), VALOR, null);
        RequestDto requestDto = new RequestDto(UUID.fromString(CODIGO_VENDEDOR), List.of(pagamentoDto));
        var requestJSON = objectMapper.writeValueAsString(requestDto);
        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo_vendedor").value(CODIGO_VENDEDOR))
                .andExpect(jsonPath("$.pagamento_list[0].codigo_cobranca").value(CODIGO_COBRANCA))
                .andExpect(jsonPath("$.pagamento_list[0].status").value("EXCEDENTE"));
    }

}
