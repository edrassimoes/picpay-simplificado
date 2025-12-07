package br.com.edras.picpaysimplificado.exception.user;

public class InvalidDocumentTypeException extends RuntimeException {

    public InvalidDocumentTypeException(String documentType) {
        super("Tipo de documento inválido: " + documentType);
    }
}
