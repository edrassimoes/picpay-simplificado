package br.com.edras.picpaysimplificado.controller;

import br.com.edras.picpaysimplificado.domain.Wallet;
import br.com.edras.picpaysimplificado.dto.wallet.AmountDTO;
import br.com.edras.picpaysimplificado.dto.wallet.WalletResponseDTO;
import br.com.edras.picpaysimplificado.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<WalletResponseDTO> getWalletByUserId(@PathVariable Long userId) {
        Wallet wallet = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok().body(new WalletResponseDTO(wallet));
    }

    @PostMapping("/user/{userId}/deposit")
    public ResponseEntity<WalletResponseDTO> deposit(
            @PathVariable Long userId,
            @Valid @RequestBody AmountDTO amount) {
        Wallet wallet = walletService.deposit(userId, amount.getAmount());
        return ResponseEntity.ok().body(new WalletResponseDTO(wallet));
    }

    @PostMapping("/user/{userId}/withdraw")
    public ResponseEntity<WalletResponseDTO> withdraw(
            @PathVariable Long userId,
            @Valid @RequestBody AmountDTO amount) {
        Wallet wallet = walletService.withdraw(userId, amount.getAmount());
        return ResponseEntity.ok().body(new WalletResponseDTO(wallet));
    }

}
