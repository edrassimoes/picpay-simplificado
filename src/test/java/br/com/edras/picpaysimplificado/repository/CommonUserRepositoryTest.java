package br.com.edras.picpaysimplificado.repository;

import br.com.edras.picpaysimplificado.domain.CommonUser;
import br.com.edras.picpaysimplificado.fixtures.CommonUserFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommonUserRepositoryTest {

    @Autowired
    private CommonUserRepository commonUserRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void save_WithValidCommonUser_ShouldPersistEntity() {
        CommonUser commonUser = commonUserRepository.save(CommonUserFixtures.createValidCommonUser());
        CommonUser persistedUser = testEntityManager.find(CommonUser.class, commonUser.getId());
        assertThat(persistedUser).isNotNull();
        assertThat(persistedUser.getName()).isEqualTo(commonUser.getName());
        assertThat(persistedUser.getEmail()).isEqualTo(commonUser.getEmail());
        assertThat(persistedUser.getPassword()).isEqualTo(commonUser.getPassword());
        assertThat(persistedUser.getUserType()).isEqualTo(commonUser.getUserType());
        assertThat(persistedUser.getCpf()).isEqualTo(commonUser.getCpf());
    }


}
