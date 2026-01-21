package br.com.edras.picpaysimplificado.fixtures;

import br.com.edras.picpaysimplificado.domain.MerchantUser;

public class MerchantUserFixtures {

    public static MerchantUser createValidMerchantUser() {
        MerchantUser user = new MerchantUser();
        user.setName("Bazar");
        user.setEmail("bazar@email.com");
        user.setPassword("bazar123");
        user.setCnpj("11581376000153");
        return user;
    }

}
