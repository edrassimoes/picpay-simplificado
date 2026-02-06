package br.com.edras.picpaysimplificado.repository;

import br.com.edras.picpaysimplificado.domain.MerchantUser;
import br.com.edras.picpaysimplificado.fixtures.MerchantUserFixtures;
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
public class MerchantUserRepositoryTest {

    @Autowired
    private MerchantUserRepository merchantUserRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void save_WithValidMerchantUser_ShouldPersistEntity() {
        MerchantUser merchantUser = MerchantUserFixtures.createValidMerchantUser();
        merchantUserRepository.save(merchantUser);

        MerchantUser persistedUser = testEntityManager.find(MerchantUser.class, merchantUser.getId());

        assertThat(persistedUser).isNotNull();
        assertThat(persistedUser.getName()).isEqualTo(merchantUser.getName());
        assertThat(persistedUser.getEmail()).isEqualTo(merchantUser.getEmail());
        assertThat(persistedUser.getPassword()).isEqualTo(merchantUser.getPassword());
        assertThat(persistedUser.getUserType()).isEqualTo(merchantUser.getUserType());
        assertThat(persistedUser.getCnpj()).isEqualTo(merchantUser.getCnpj());
    }

    /*

    Movido para UserRepository (Onde a validação do e-mail existe)

    @Test
    void save_WithExistingEmail_ShouldThrowDataIntegrityViolationException() {
        MerchantUser user1 = MerchantUserFixtures.createValidMerchantUser();
        merchantUserRepository.save(user1);

        MerchantUser user2 = MerchantUserFixtures.createValidMerchantUser();
        user2.setCnpj("11144477735");

        assertThrows(DataIntegrityViolationException.class, () -> {
            merchantUserRepository.saveAndFlush(user2);
        });
    }

     */

    @Test
    void save_WithExistingCnpj_ShouldThrowDataIntegrityViolationException() {
        MerchantUser user1 = merchantUserRepository.save(
                MerchantUserFixtures.createValidMerchantUser()
        );

        MerchantUser user2 = MerchantUserFixtures.createValidMerchantUser();
        user2.setEmail("outro@email.com");

        assertThrows(DataIntegrityViolationException.class, () -> {
            merchantUserRepository.saveAndFlush(user2);
        });
    }

    @Test
    void existsByCnpj_WhenCnpjExists_ShouldReturnTrue() {
        MerchantUser merchantUser = MerchantUserFixtures.createValidMerchantUser();
        testEntityManager.persist(merchantUser);
        boolean exists = merchantUserRepository.existsByCnpj(merchantUser.getCnpj());
        assertThat(exists).isTrue();
    }

    @Test
    void existsByCnpj_WhenCnpjDoesNotExist_ShouldReturnFalse() {
        boolean exists = merchantUserRepository.existsByCnpj("12345678900");
        assertThat(exists).isFalse();
    }

    @Test
    void save_WithNullCnpj_ShouldPersistEntity() {
        MerchantUser merchantUser = MerchantUserFixtures.createValidMerchantUser();
        merchantUser.setCnpj(null);

        MerchantUser saved = merchantUserRepository.saveAndFlush(merchantUser);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCnpj()).isNull();
    }

}
