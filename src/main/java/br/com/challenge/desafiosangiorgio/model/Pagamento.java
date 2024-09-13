package br.com.challenge.desafiosangiorgio.model;

import br.com.challenge.desafiosangiorgio.model.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name="pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID codigoPagamento;
    private UUID codigoCobranca;
    private BigDecimal valor;
    private StatusPagamento status;

}
