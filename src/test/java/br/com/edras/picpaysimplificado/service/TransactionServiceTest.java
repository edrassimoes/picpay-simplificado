package br.com.edras.picpaysimplificado.service;

import br.com.edras.picpaysimplificado.domain.CommonUser;
import br.com.edras.picpaysimplificado.domain.MerchantUser;
import br.com.edras.picpaysimplificado.domain.Transaction;
import br.com.edras.picpaysimplificado.domain.enums.TransactionStatus;
import br.com.edras.picpaysimplificado.dto.transaction.TransactionRequestDTO;
import br.com.edras.picpaysimplificado.dto.transaction.TransactionResponseDTO;
import br.com.edras.picpaysimplificado.exception.transaction.MerchantCannotTransferException;
import br.com.edras.picpaysimplificado.exception.transaction.SameUserTransactionException;
import br.com.edras.picpaysimplificado.exception.transaction.TransactionNotAuthorizedException;
import br.com.edras.picpaysimplificado.fixtures.CommonUserFixtures;
import br.com.edras.picpaysimplificado.fixtures.MerchantUserFixtures;
import br.com.edras.picpaysimplificado.repository.TransactionRepository;
import br.com.edras.picpaysimplificado.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void transfer_WhenSuccessful_ShouldCompleteTransaction() {
        CommonUser payer = CommonUserFixtures.createValidCommonUser();
        CommonUser payee = CommonUserFixtures.createValidCommonUser();
        payer.setId(1L);
        payee.setId(2L);

        TransactionRequestDTO requestDTO = new TransactionRequestDTO(50.00, payer.getId(), payee.getId());

        when(userRepository.findById(payer.getId())).thenReturn(Optional.of(payer));
        when(userRepository.findById(payee.getId())).thenReturn(Optional.of(payee));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponseDTO response = transactionService.transfer(requestDTO, TransactionStatus.AUTHORIZED);

        assertThat(response).isNotNull();

        verify(walletService).withdraw(payer.getId(), requestDTO.getAmount());
        verify(walletService).deposit(payee.getId(), requestDTO.getAmount());
    }

    @Test
    void transfer_WhenPayerIsPayee_ShouldThrowException() {
        Long userId = 1L;

        TransactionRequestDTO requestDTO = new TransactionRequestDTO(50.00, userId, userId);

        assertThrows(SameUserTransactionException.class, () -> {
            transactionService.transfer(requestDTO, TransactionStatus.AUTHORIZED);
        });

        verify(transactionRepository, never()).save(any());
        verify(walletService, never()).withdraw(any(), any());
        verify(walletService, never()).deposit(any(), any());
    }

    @Test
    void transfer_WhenPayerIsMerchant_ShouldThrowException() {
        MerchantUser payer = MerchantUserFixtures.createValidMerchantUser();
        CommonUser payee = CommonUserFixtures.createValidCommonUser();
        payer.setId(1L);
        payee.setId(2L);

        TransactionRequestDTO requestDTO = new TransactionRequestDTO(50.00, payer.getId(), payee.getId());

        when(userRepository.findById(payer.getId())).thenReturn(Optional.of(payer));

        assertThrows(MerchantCannotTransferException.class, () -> {
            transactionService.transfer(requestDTO, TransactionStatus.AUTHORIZED);
        });

        verify(transactionRepository, never()).save(any());
        verify(walletService, never()).withdraw(any(), any());
        verify(walletService, never()).deposit(any(), any());
    }

    @Test
    void transfer_WhenTransactionNotAuthorized_ShouldThrowException() {
        CommonUser payer = CommonUserFixtures.createValidCommonUser();
        CommonUser payee = CommonUserFixtures.createValidCommonUser();
        payer.setId(1L);
        payee.setId(2L);

        TransactionRequestDTO requestDTO = new TransactionRequestDTO(50.00, payer.getId(), payee.getId());

        when(userRepository.findById(payer.getId())).thenReturn(Optional.of(payer));
        when(userRepository.findById(payee.getId())).thenReturn(Optional.of(payee));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        assertThrows(TransactionNotAuthorizedException.class, () -> {
            transactionService.transfer(requestDTO, TransactionStatus.FAILED);
        });

        verify(walletService, never()).withdraw(any(), any());
        verify(walletService, never()).deposit(any(), any());
    }

}