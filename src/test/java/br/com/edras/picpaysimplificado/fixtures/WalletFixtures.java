package br.com.edras.picpaysimplificado.fixtures;

import br.com.edras.picpaysimplificado.domain.User;
import br.com.edras.picpaysimplificado.domain.Wallet;

public class WalletFixtures {

    public static Wallet createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(100.0);
        return wallet;
    }

    public static Wallet createWalletWithInitialBalance(User user, Double balance) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(balance);
        return wallet;
    }

}