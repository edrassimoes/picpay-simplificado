package br.com.edras.picpaysimplificado.exception;

public class DocumentAlreadyExistsException extends RuntimeException {
    
    public DocumentAlreadyExistsException(String document) {
        super("CPF/CNPJ já cadastrado: " + document);
    }
}
