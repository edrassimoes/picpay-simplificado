package br.com.edras.picpaysimplificado.repository;

import br.com.edras.picpaysimplificado.domain.CommonUser;
import br.com.edras.picpaysimplificado.domain.Transaction;
import br.com.edras.picpaysimplificado.domain.User;
import br.com.edras.picpaysimplificado.domain.enums.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void findByUserId_ShouldReturnTransactions() {
        User user1 = new CommonUser("User 1", "user1@test.com", "pass", "111.111.111-11");
        User user2 = new CommonUser("User 2", "user2@test.com", "pass", "222.222.222-22");
        User user3 = new CommonUser("User 3", "user3@test.com", "pass", "333.333.333-33");

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);

        Transaction transaction1 = new Transaction(100.0, user1, user2, LocalDateTime.now(), TransactionStatus.COMPLETED);
        Transaction transaction2 = new Transaction(50.0, user2, user1, LocalDateTime.now(), TransactionStatus.COMPLETED);
        Transaction transaction3 = new Transaction(25.0, user3, user2, LocalDateTime.now(), TransactionStatus.COMPLETED); // user1 não participa

        entityManager.persist(transaction1);
        entityManager.persist(transaction2);
        entityManager.persist(transaction3);

        List<Transaction> result = transactionRepository.findByUserId(user1.getId());

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(transaction1, transaction2);
        assertThat(result).doesNotContain(transaction3);
    }

    @Test
    void existsByPayerId_ShouldReturnTrue_WhenUserIsPayer() {
        User payer = new CommonUser("Payer", "payer@test.com", "pass", "111.111.111-11");
        User payee = new CommonUser("Payee", "payee@test.com", "pass", "222.222.222-22");
        entityManager.persist(payer);
        entityManager.persist(payee);

        Transaction transaction = new Transaction(100.0, payer, payee, LocalDateTime.now(), TransactionStatus.COMPLETED);
        entityManager.persist(transaction);

        boolean result = transactionRepository.existsByPayerId(payer.getId());

        assertThat(result).isTrue();
    }

    @Test
    void existsByPayerId_ShouldReturnFalse_WhenUserIsNotPayer() {
        User payer = new CommonUser("Payer", "payer@test.com", "pass", "111.111.111-11");
        User payee = new CommonUser("Payee", "payee@test.com", "pass", "222.222.222-22");
        entityManager.persist(payer);
        entityManager.persist(payee);

        Transaction transaction = new Transaction(100.0, payer, payee, LocalDateTime.now(), TransactionStatus.COMPLETED);
        entityManager.persist(transaction);

        boolean result = transactionRepository.existsByPayerId(payee.getId());

        assertThat(result).isFalse();
    }

    @Test
    void existsByPayeeId_ShouldReturnTrue_WhenUserIsPayee() {
        User payer = new CommonUser("Payer", "payer@test.com", "pass", "111.111.111-11");
        User payee = new CommonUser("Payee", "payee@test.com", "pass", "222.222.222-22");
        entityManager.persist(payer);
        entityManager.persist(payee);

        Transaction transaction = new Transaction(100.0, payer, payee, LocalDateTime.now(), TransactionStatus.COMPLETED);
        entityManager.persist(transaction);

        boolean result = transactionRepository.existsByPayeeId(payee.getId());

        assertThat(result).isTrue();
    }

    @Test
    void existsByPayeeId_ShouldReturnFalse_WhenUserIsNotPayee() {
        User payer = new CommonUser("Payer", "payer@test.com", "pass", "111.111.111-11");
        User payee = new CommonUser("Payee", "payee@test.com", "pass", "222.222.222-22");
        entityManager.persist(payer);
        entityManager.persist(payee);

        Transaction transaction = new Transaction(100.0, payer, payee, LocalDateTime.now(), TransactionStatus.COMPLETED);
        entityManager.persist(transaction);

        boolean result = transactionRepository.existsByPayeeId(payer.getId());

        assertThat(result).isFalse();
    }

}