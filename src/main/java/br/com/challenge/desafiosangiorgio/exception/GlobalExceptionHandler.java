package br.com.challenge.desafiosangiorgio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final static String COBRANCA_EXCEPTION_MESSAGE = "Cobrança não encontrada para o código informado";
    private final static String VENDEDOR_EXCEPTION_MESSAGE = "Vendedor não encontrada para o código informado";
    private final static String GLOBAL_EXCEPTION_MESSAGE = "Houve um erro ao processar a requisição";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(GLOBAL_EXCEPTION_MESSAGE + ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CobrancaNaoEncontradaException.class)
    public final ResponseEntity<Object> handleCobrancaException(CobrancaNaoEncontradaException ex, WebRequest request) {
        return new ResponseEntity<>(COBRANCA_EXCEPTION_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VendedorNaoEncontradoException.class)
    public final ResponseEntity<Object> handleVendedorException(VendedorNaoEncontradoException ex, WebRequest request) {
        return new ResponseEntity<>(VENDEDOR_EXCEPTION_MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
