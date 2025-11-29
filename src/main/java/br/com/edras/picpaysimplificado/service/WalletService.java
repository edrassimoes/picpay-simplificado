package br.com.edras.picpaysimplificado.service;

import br.com.edras.picpaysimplificado.domain.User;
import br.com.edras.picpaysimplificado.domain.Wallet;
import br.com.edras.picpaysimplificado.domain.enums.UserType;
import br.com.edras.picpaysimplificado.dto.wallet.WalletResponseDTO;
import br.com.edras.picpaysimplificado.exception.user.UserNotFoundException;
import br.com.edras.picpaysimplificado.exception.wallet.InsufficientBalanceException;
import br.com.edras.picpaysimplificado.exception.wallet.InvalidAmountException;
import br.com.edras.picpaysimplificado.exception.wallet.MerchantCannotDepositException;
import br.com.edras.picpaysimplificado.exception.wallet.WalletNotFoundException;
import br.com.edras.picpaysimplificado.repository.UserRepository;
import br.com.edras.picpaysimplificado.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    public Wallet createWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet getWalletByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Wallet wallet = user.getWallet();
        if (wallet == null) {
            throw new WalletNotFoundException(userId);
        }
        return wallet;
    }

    public Double getBalanceByUserId(Long userId) {
        Wallet wallet = getWalletByUserId(userId);
        return wallet.getBalance();
    }

    @Transactional
    public Wallet deposit(Long userId, Double amount) {
        if (amount <= 0){
            throw new InvalidAmountException(amount);
        }
        Wallet wallet = getWalletByUserId(userId);
        if (wallet.getUser().getUserType() == UserType.MERCHANT) {
            throw new MerchantCannotDepositException();
        }
        wallet.setBalance(wallet.getBalance() + amount);
        return wallet;
    }

    @Transactional
    public Wallet withdraw(Long userId, Double amount) {
        if (amount <= 0){
            throw new InvalidAmountException(amount);
        }
        Wallet wallet = getWalletByUserId(userId);
        if (wallet.getBalance() < amount) {
            throw new InsufficientBalanceException();
        }
        wallet.setBalance(wallet.getBalance() - amount);
        return wallet;
    }

}
