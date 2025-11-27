package br.com.edras.picpaysimplificado.service;

import br.com.edras.picpaysimplificado.domain.User;
import br.com.edras.picpaysimplificado.domain.Wallet;
import br.com.edras.picpaysimplificado.exception.UserNotFoundException;
import br.com.edras.picpaysimplificado.repository.UserRepository;
import br.com.edras.picpaysimplificado.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final UserRepository userRepository;
    private WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    public Wallet getWalletByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return user.getWallet();
    }

    public Double getBalanceByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return user.getWallet().getBalance();
    }

    public

}
