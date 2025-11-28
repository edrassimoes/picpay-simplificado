package br.com.edras.picpaysimplificado.exception.user;

public class InvalidUserTypeException extends RuntimeException {
    
    public InvalidUserTypeException(String userType) {
        super("Tipo de usuário inválido: " + userType + ". Use 'COMMON' ou 'MERCHANT'");
    }
}
