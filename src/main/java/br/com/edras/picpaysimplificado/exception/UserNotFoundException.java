package br.com.edras.picpaysimplificado.exception;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(Long id) {
        super("Usuário não encontrado com ID: " + id);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
