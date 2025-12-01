package br.com.edras.picpaysimplificado.exception.transaction;

public class MerchantCannotTransferException extends RuntimeException {
    public MerchantCannotTransferException(Long userId) {
        super("Lojistas não podem realizar transferências. Usuário ID: " + userId);
    }
}
