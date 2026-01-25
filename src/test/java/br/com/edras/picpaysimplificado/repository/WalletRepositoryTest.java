package br.com.edras.picpaysimplificado.repository;

import br.com.edras.picpaysimplificado.domain.CommonUser;
import br.com.edras.picpaysimplificado.domain.MerchantUser;
import br.com.edras.picpaysimplificado.domain.Wallet;
import br.com.edras.picpaysimplificado.fixtures.CommonUserFixtures;
import br.com.edras.picpaysimplificado.fixtures.MerchantUserFixtures;
import br.com.edras.picpaysimplificado.fixtures.WalletFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void save_WithValidCommonUser_ShouldPersistWallet() {
        CommonUser user = CommonUserFixtures.createValidCommonUser();
        testEntityManager.persist(user);

        Wallet wallet = WalletFixtures.createWallet(user);
        walletRepository.save(wallet);

        Wallet persistedWallet = testEntityManager.find(Wallet.class, wallet.getId());

        assertThat(persistedWallet).isNotNull();
        assertThat(persistedWallet.getBalance()).isEqualTo(wallet.getBalance());
        assertThat(persistedWallet.getUser()).isEqualTo(user);
    }

    @Test
    void save_WithValidMerchantUser_ShouldPersistWallet() {
        MerchantUser user = MerchantUserFixtures.createValidMerchantUser();
        testEntityManager.persist(user);

        Wallet wallet = WalletFixtures.createWallet(user);
        walletRepository.save(wallet);

        Wallet persistedWallet = testEntityManager.find(Wallet.class, wallet.getId());

        assertThat(persistedWallet).isNotNull();
        assertThat(persistedWallet.getBalance()).isEqualTo(wallet.getBalance());
        assertThat(persistedWallet.getUser()).isEqualTo(user);
    }

}