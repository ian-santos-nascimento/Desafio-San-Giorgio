package br.com.challenge.desafiosangiorgio.repository;

import br.com.challenge.desafiosangiorgio.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendedorRepository extends JpaRepository<Vendedor, UUID> {
}
