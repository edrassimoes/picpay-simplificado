package br.com.edras.picpaysimplificado.repository;

import br.com.edras.picpaysimplificado.domain.CommonUser;
import br.com.edras.picpaysimplificado.fixtures.CommonUserFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@DataJpaTest
public class CommonUserRepositoryTest {

    @Autowired
    private CommonUserRepository commonUserRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void save_WithValidCommonUser_ShouldPersistEntity() {
        CommonUser commonUser = CommonUserFixtures.createValidCommonUser();
        commonUserRepository.save(commonUser);

        CommonUser persistedUser = testEntityManager.find(CommonUser.class, commonUser.getId());

        assertThat(persistedUser).isNotNull();
        assertThat(persistedUser.getName()).isEqualTo(commonUser.getName());
        assertThat(persistedUser.getEmail()).isEqualTo(commonUser.getEmail());
        assertThat(persistedUser.getPassword()).isEqualTo(commonUser.getPassword());
        assertThat(persistedUser.getUserType()).isEqualTo(commonUser.getUserType());
        assertThat(persistedUser.getCpf()).isEqualTo(commonUser.getCpf());
    }

    /*

    Movido para UserRepository (Onde a validação do e-mail existe)

    @Test
    void save_WithExistingEmail_ShouldThrowDataIntegrityViolationException() {
        CommonUser user1 = CommonUserFixtures.createValidCommonUser();
        commonUserRepository.save(user1);

        CommonUser user2 = CommonUserFixtures.createValidCommonUser();
        user2.setCpf("11144477735");

        assertThrows(DataIntegrityViolationException.class, () -> {
            commonUserRepository.saveAndFlush(user2);
        });
    }

     */

    @Test
    void save_WithExistingCpf_ShouldThrowDataIntegrityViolationException() {
        CommonUser user1 = commonUserRepository.save(
                CommonUserFixtures.createValidCommonUser()
        );

        CommonUser user2 = CommonUserFixtures.createValidCommonUser();
        user2.setEmail("outro@email.com");

        assertThrows(DataIntegrityViolationException.class, () -> {
            commonUserRepository.saveAndFlush(user2);
        });
    }

    @Test
    void existsByCpf_WhenCpfExists_ShouldReturnTrue() {
        CommonUser commonUser = CommonUserFixtures.createValidCommonUser();
        testEntityManager.persist(commonUser);
        boolean exists = commonUserRepository.existsByCpf(commonUser.getCpf());
        assertThat(exists).isTrue();
    }

    @Test
    void existsByCpf_WhenCpfDoesNotExist_ShouldReturnFalse() {
        boolean exists = commonUserRepository.existsByCpf("12345678900");
        assertThat(exists).isFalse();
    }

    @Test
    void save_WithNullCpf_ShouldPersistEntity() {
        CommonUser commonUser = CommonUserFixtures.createValidCommonUser();
        commonUser.setCpf(null);

        CommonUser saved = commonUserRepository.saveAndFlush(commonUser);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCpf()).isNull();
    }

}
