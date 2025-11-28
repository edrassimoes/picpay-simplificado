package br.com.edras.picpaysimplificado.exception.wallet;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(Long userId) {
        super("Carteira não encontrada para o id: " + userId);
    }
}
