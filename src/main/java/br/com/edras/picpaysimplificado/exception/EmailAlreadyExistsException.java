package br.com.edras.picpaysimplificado.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    
    public EmailAlreadyExistsException(String email) {
        super("Email já cadastrado: " + email);
    }
}
