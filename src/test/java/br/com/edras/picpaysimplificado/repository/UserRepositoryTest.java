package br.com.edras.picpaysimplificado.repository;

import br.com.edras.picpaysimplificado.domain.CommonUser;
import br.com.edras.picpaysimplificado.domain.MerchantUser;
import br.com.edras.picpaysimplificado.domain.User;
import br.com.edras.picpaysimplificado.fixtures.CommonUserFixtures;
import br.com.edras.picpaysimplificado.fixtures.MerchantUserFixtures;
import br.com.edras.picpaysimplificado.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void existsByEmail_WhenCommonUserEmailExists_ShouldReturnTrue() {
        CommonUser user = CommonUserFixtures.createValidCommonUser();
        testEntityManager.persist(user);

        boolean exists = userRepository.existsByEmail(user.getEmail());

        assertThat(exists).isTrue();
    }

    @Test
    void existsByEmail_WhenMerchantUserEmailExists_ShouldReturnTrue() {
        MerchantUser user = MerchantUserFixtures.createValidMerchantUser();
        testEntityManager.persist(user);

        boolean exists = userRepository.existsByEmail(user.getEmail());

        assertThat(exists).isTrue();
    }

    @Test
    void existsByEmail_WhenEmailDoesNotExist_ShouldReturnFalse() {
        boolean exists = userRepository.existsByEmail("naoexiste@email.com");

        assertThat(exists).isFalse();
    }

    @Test
    void findByEmail_WhenCommonUserEmailExists_ShouldReturnUser() {
        CommonUser user = CommonUserFixtures.createValidCommonUser();
        testEntityManager.persist(user);

        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    void findByEmail_WhenMerchantUserEmailExists_ShouldReturnUser() {
        MerchantUser user = MerchantUserFixtures.createValidMerchantUser();
        testEntityManager.persist(user);

        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    void findByEmail_WhenEmailDoesNotExist_ShouldReturnEmpty() {
        Optional<User> foundUser = userRepository.findByEmail("naoexiste@email.com");

        assertThat(foundUser).isNotPresent();
    }

    @Test
    void save_WithExistingEmail_ShouldThrowDataIntegrityViolationException() {
        CommonUser user1 = CommonUserFixtures.createValidCommonUser();
        testEntityManager.persistAndFlush(user1);

        MerchantUser user2 = MerchantUserFixtures.createValidMerchantUser();
        user2.setEmail(user1.getEmail());

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.saveAndFlush(user2);
        });
    }

}