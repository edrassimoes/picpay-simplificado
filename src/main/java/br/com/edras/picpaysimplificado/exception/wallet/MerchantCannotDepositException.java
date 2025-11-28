package br.com.edras.picpaysimplificado.exception.wallet;

public class MerchantCannotDepositException extends RuntimeException {
    public MerchantCannotDepositException() {
        super("Operação inválida para usuários do tipo Lojista");
    }
}
