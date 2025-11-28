package br.com.edras.picpaysimplificado.exception.user;

public class EmailAlreadyExistsException extends RuntimeException {
    
    public EmailAlreadyExistsException(String email) {
        super("Email já cadastrado: " + email);
    }
}
