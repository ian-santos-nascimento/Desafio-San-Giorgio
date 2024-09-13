package br.com.challenge.desafiosangiorgio.service;

import br.com.challenge.desafiosangiorgio.exception.CobrancaNaoEncontradaException;
import br.com.challenge.desafiosangiorgio.exception.VendedorNaoEncontradoException;
import br.com.challenge.desafiosangiorgio.repository.CobrancaRepository;
import br.com.challenge.desafiosangiorgio.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidatorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private CobrancaRepository cobrancaRepository;

    public void validarVendedor(UUID codigoVendedor) {
        if (!vendedorRepository.existsById(codigoVendedor)) {
            throw new VendedorNaoEncontradoException("Vendedor não encontrado");
        }
    }

    public void validarCobranca(UUID codigoCobranca) {
        if (!cobrancaRepository.existsById(codigoCobranca)) {
            throw new CobrancaNaoEncontradaException("Cobrança não encontrada");
        }
    }
}
