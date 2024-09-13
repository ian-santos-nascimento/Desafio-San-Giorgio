package br.com.challenge.desafiosangiorgio.repository;

import br.com.challenge.desafiosangiorgio.model.Cobranca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CobrancaRepository extends JpaRepository<Cobranca, UUID> {
}
