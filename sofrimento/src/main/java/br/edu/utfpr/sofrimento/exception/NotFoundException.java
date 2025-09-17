package br.edu.utfpr.sofrimento.exception;

/**
 * Exceção personalizada para indicar que um recurso não foi encontrado.
 * 
 * Esta exceção estende RuntimeException, permitindo que seja lançada
 * sem a necessidade de declaração explícita.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
