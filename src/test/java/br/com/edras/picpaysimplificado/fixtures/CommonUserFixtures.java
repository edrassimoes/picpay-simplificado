package br.com.edras.picpaysimplificado.fixtures;

import br.com.edras.picpaysimplificado.domain.CommonUser;

public class CommonUserFixtures {

    public static CommonUser createValidCommonUser() {
        CommonUser user = new CommonUser();
        user.setName("João");
        user.setEmail("joao@email.com");
        user.setPassword("senha123");
        user.setCpf("12345678900");
        return user;
    }

}
