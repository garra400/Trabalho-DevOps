package br.edu.utfpr.sofrimento.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe para tratamento global de exceções na aplicação.
 * Ela captura exceções específicas e retorna respostas HTTP apropriadas.
 * 
 * Atualmente, ela trata a exceção NotFoundException, retornando um status 404.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePessoaNotFound(NotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }
}
