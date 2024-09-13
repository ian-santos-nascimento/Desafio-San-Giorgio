package br.com.challenge.desafiosangiorgio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

public record RequestDto(
        @Schema(example = "e154ddc7-48a8-4336-9199-73a56137e220") UUID codigo_vendedor, @JsonProperty(value = "pagamento_list") List<PagamentoDto> pagamentoList) {
}
