package br.com.challenge.desafiosangiorgio.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name="vendedor")
@Data
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID codigoVendedor;

    private String nome;
}
