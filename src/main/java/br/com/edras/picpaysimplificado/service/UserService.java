package br.com.edras.picpaysimplificado.service;

import br.com.edras.picpaysimplificado.domain.CommonUser;
import br.com.edras.picpaysimplificado.domain.MerchantUser;
import br.com.edras.picpaysimplificado.domain.User;
import br.com.edras.picpaysimplificado.domain.Wallet;
import br.com.edras.picpaysimplificado.domain.enums.UserType;
import br.com.edras.picpaysimplificado.dto.user.UserRequestDTO;
import br.com.edras.picpaysimplificado.dto.user.UserResponseDTO;
import br.com.edras.picpaysimplificado.dto.user.UserUpdateDTO;
import br.com.edras.picpaysimplificado.exception.user.*;
import br.com.edras.picpaysimplificado.repository.CommonUserRepository;
import br.com.edras.picpaysimplificado.repository.MerchantUserRepository;
import br.com.edras.picpaysimplificado.repository.TransactionRepository;
import br.com.edras.picpaysimplificado.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MerchantUserRepository merchantUserRepository;
    private final CommonUserRepository commonUserRepository;
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;

    public UserService(UserRepository userRepository, MerchantUserRepository merchantUserRepository, CommonUserRepository commonUserRepository, TransactionRepository transactionRepository, WalletService walletService) {
        this.userRepository = userRepository;
        this.merchantUserRepository = merchantUserRepository;
        this.commonUserRepository = commonUserRepository;
        this.transactionRepository = transactionRepository;
        this.walletService = walletService;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
             throw new EmailAlreadyExistsException(dto.getEmail());
        }

        User user;

        if (dto.getUserType() == UserType.COMMON) {

            if (dto.getCpf() == null || dto.getCpf().isBlank()) {
                throw new IllegalArgumentException("CPF é obrigatório para usuários comuns");
            }

            if (commonUserRepository.existsByCpf(dto.getCpf())) {
             throw new DocumentAlreadyExistsException(dto.getCpf());
            }

            if (dto.getCnpj() != null || !dto.getCnpj().isBlank()) {
                throw new InvalidDocumentTypeException("CNPJ não é permitido a usuários comuns");
            }

            user = new CommonUser(dto.getName(), dto.getEmail(), dto.getPassword(), dto.getCpf());

        } else {

            if (dto.getCnpj() == null || dto.getCnpj().isBlank()) {
                throw new IllegalArgumentException("CNPJ é obrigatório para usuários lojistas");
            }

            if (merchantUserRepository.existsByCnpj(dto.getCnpj())) {
                 throw new DocumentAlreadyExistsException(dto.getCnpj());
            }

            if (dto.getCpf() != null || !dto.getCpf().isBlank()) {
                throw new InvalidDocumentTypeException("CPF não é permitido a usuários lojistas");
            }

            user = new MerchantUser(dto.getName(), dto.getEmail(), dto.getPassword(), dto.getCnpj());
        }

        User savedUser = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        wallet.setBalance(0.0);
        walletService.createOrUpdateWallet(wallet);

        return new UserResponseDTO(savedUser);
    }

    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            existingUser.setName(dto.getName());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            if (!existingUser.getEmail().equals(dto.getEmail()) &&
                 userRepository.existsByEmail(dto.getEmail())) {
                 throw new EmailAlreadyExistsException(dto.getEmail());
            }
            existingUser.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingUser.setPassword(dto.getPassword());
        }

        User updatedUser = userRepository.save(existingUser);
        return new UserResponseDTO(updatedUser);
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        if (transactionRepository.existsByPayerId(id)
                || transactionRepository.existsByPayeeId(id)) {

            throw new UserHasTransactionsException();
        }

        userRepository.deleteById(id);
    }

}
