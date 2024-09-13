package br.com.challenge.desafiosangiorgio.repository;

import br.com.challenge.desafiosangiorgio.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
}
