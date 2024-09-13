package br.com.challenge.desafiosangiorgio.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table
public class Cobranca {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID codigoCobranca;
    private BigDecimal valor;
}
