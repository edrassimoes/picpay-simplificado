package br.com.edras.picpaysimplificado.exception.user;

public class DocumentAlreadyExistsException extends RuntimeException {
    
    public DocumentAlreadyExistsException(String document) {
        super("CPF/CNPJ já cadastrado: " + document);
    }
}
