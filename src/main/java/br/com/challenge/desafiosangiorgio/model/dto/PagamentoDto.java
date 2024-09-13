package br.com.challenge.desafiosangiorgio.model.dto;

import br.com.challenge.desafiosangiorgio.model.enums.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PagamentoDto {
    @JsonProperty(value = "codigo_cobranca")
    @Schema(example = "a1e724f9-f98f-43ad-96b6-3d4c1256b164")
    private UUID codigoCobranca;
    @NotNull(message = "O valor não pode ser nulo!")
    private BigDecimal valor;
    private StatusPagamento status;

}