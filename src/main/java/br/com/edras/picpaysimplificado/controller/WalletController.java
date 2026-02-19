package br.com.edras.picpaysimplificado.controller;

import br.com.edras.picpaysimplificado.domain.Wallet;
import br.com.edras.picpaysimplificado.dto.wallet.AmountDTO;
import br.com.edras.picpaysimplificado.dto.wallet.WalletResponseDTO;
import br.com.edras.picpaysimplificado.service.WalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Wallets")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Operation(summary = "Get wallet by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet found successfully"),
            @ApiResponse(responseCode = "404", description = "User or wallet not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<WalletResponseDTO> getWalletByUserId(@PathVariable Long userId) {
        Wallet wallet = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok().body(new WalletResponseDTO(wallet));
    }

    @Operation(summary = "Deposit amount into user wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposit successful"),
            @ApiResponse(responseCode = "400", description = "Invalid amount"),
            @ApiResponse(responseCode = "403", description = "Merchant users cannot deposit"),
            @ApiResponse(responseCode = "404", description = "User or wallet not found")
    })
    @PatchMapping("/user/{userId}/deposit")
    public ResponseEntity<WalletResponseDTO> deposit(
            @PathVariable Long userId,
            @Valid @RequestBody AmountDTO amount) {
        Wallet wallet = walletService.deposit(userId, amount.getAmount());
        return ResponseEntity.ok(new WalletResponseDTO(wallet));
    }

    @Operation(summary = "Withdraw amount from user wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdraw successful"),
            @ApiResponse(responseCode = "400", description = "Invalid amount or insufficient balance"),
            @ApiResponse(responseCode = "404", description = "User or wallet not found")
    })
    @PatchMapping("/user/{userId}/withdraw")
    public ResponseEntity<WalletResponseDTO> withdraw(
            @PathVariable Long userId,
            @Valid @RequestBody AmountDTO amount) {
        Wallet wallet = walletService.withdraw(userId, amount.getAmount());
        return ResponseEntity.ok(new WalletResponseDTO(wallet));
    }

}
