package br.com.edras.picpaysimplificado.fixtures;

import br.com.edras.picpaysimplificado.domain.CommonUser;
import br.com.edras.picpaysimplificado.domain.Wallet;

public class CommonUserFixtures {

    public static CommonUser createValidCommonUser() {
        CommonUser user = new CommonUser();
        user.setName("João");
        user.setEmail("joao@email.com");
        user.setPassword("senha123");
        user.setCpf("12345678900");
        return user;
    }

    public static CommonUser createCommonUserWithWallet(Wallet wallet) {
        CommonUser user = createValidCommonUser();
        user.setWallet(wallet);
        return user;
    }

}
