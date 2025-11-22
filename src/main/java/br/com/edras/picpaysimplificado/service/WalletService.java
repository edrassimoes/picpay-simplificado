package br.com.edras.picpaysimplificado.service;

import br.com.edras.picpaysimplificado.domain.Wallet;
import br.com.edras.picpaysimplificado.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet getWalletByUserId(Long userId) {
        return walletRepository.findById(userId).orElse(null);
    }

}
