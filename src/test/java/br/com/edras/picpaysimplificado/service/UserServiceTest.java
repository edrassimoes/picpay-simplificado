package br.com.edras.picpaysimplificado.service;

import br.com.edras.picpaysimplificado.domain.CommonUser;
import br.com.edras.picpaysimplificado.domain.MerchantUser;
import br.com.edras.picpaysimplificado.domain.User;
import br.com.edras.picpaysimplificado.domain.enums.UserType;
import br.com.edras.picpaysimplificado.dto.user.UserRequestDTO;
import br.com.edras.picpaysimplificado.dto.user.UserResponseDTO;
import br.com.edras.picpaysimplificado.exception.user.*;
import br.com.edras.picpaysimplificado.repository.CommonUserRepository;
import br.com.edras.picpaysimplificado.repository.MerchantUserRepository;
import br.com.edras.picpaysimplificado.repository.TransactionRepository;
import br.com.edras.picpaysimplificado.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MerchantUserRepository merchantUserRepository;

    @Mock
    private CommonUserRepository commonUserRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private WalletService walletService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO commonUserRequestDTO;
    private UserRequestDTO merchantUserRequestDTO;
    private User commonUser;
    private User merchantUser;

    @BeforeEach
    void setUp() {
        commonUserRequestDTO = new UserRequestDTO("Test User", "test@test.com", "password", UserType.COMMON, "123.456.789-00", null);
        merchantUserRequestDTO = new UserRequestDTO("Test User", "test@test.com", "password", UserType.MERCHANT, null, "73922156000187");
        commonUser = new CommonUser("Test User", "test@test.com", "encodedPassword", "123.456.789-00");
        merchantUser = new MerchantUser("Test User", "test@test.com", "encodedPassword", "73922156000187");;

    }

    @Test
    void createUser_ShouldCreateCommonUser_WhenDataIsValid() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(commonUserRepository.existsByCpf(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(commonUser);
        when(walletService.createOrUpdateWallet(any())).thenReturn(null);

        UserResponseDTO result = userService.createUser(commonUserRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(commonUserRequestDTO.getName());
        assertThat(result.getUserType()).isEqualTo(commonUserRequestDTO.getUserType());

        verify(userRepository).save(any(CommonUser.class));
        verify(walletService).createOrUpdateWallet(any());
    }

    @Test
    void createUser_ShouldCreateMerchantUser_WhenDataIsValid() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(merchantUserRepository.existsByCnpj(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(merchantUser);
        when(walletService.createOrUpdateWallet(any())).thenReturn(null);

        UserResponseDTO result = userService.createUser(merchantUserRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(merchantUserRequestDTO.getName());
        assertThat(result.getUserType()).isEqualTo(merchantUserRequestDTO.getUserType());

        verify(userRepository).save(any(MerchantUser.class));
        verify(walletService).createOrUpdateWallet(any());
    }

    @Test
    void createUser_ShouldThrowInvalidDocumentTypeException_ForCommonUserWithCnpj() {
        commonUserRequestDTO.setCnpj("73922156000187");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(commonUserRepository.existsByCpf(anyString())).thenReturn(false);

        assertThatThrownBy(() -> userService.createUser(commonUserRequestDTO))
                .isInstanceOf(InvalidDocumentTypeException.class);
    }

    @Test
    void createUser_ShouldThrowInvalidDocumentTypeException_ForMerchantUserWithCpf() {
        merchantUserRequestDTO.setCpf("123.456.789-00");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(merchantUserRepository.existsByCnpj(anyString())).thenReturn(false);

        assertThatThrownBy(() -> userService.createUser(merchantUserRequestDTO))
                .isInstanceOf(InvalidDocumentTypeException.class);
    }

    @Test
    void createUser_ShouldThrowEmailAlreadyExistsException_WhenEmailExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(commonUserRequestDTO))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("Email já cadastrado: " + commonUserRequestDTO.getEmail());

        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_ShouldThrowDocumentAlreadyExistsException_WhenCpfExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(commonUserRepository.existsByCpf(anyString())).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(commonUserRequestDTO))
                .isInstanceOf(DocumentAlreadyExistsException.class)
                .hasMessageContaining("CPF/CNPJ já cadastrado: " + commonUserRequestDTO.getCpf());

        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_ShouldThrowDocumentAlreadyExistsException_WhenCnpjExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(merchantUserRepository.existsByCnpj(anyString())).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(merchantUserRequestDTO))
                .isInstanceOf(DocumentAlreadyExistsException.class)
                .hasMessageContaining("CPF/CNPJ já cadastrado: " + merchantUserRequestDTO.getCnpj());

        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_ShouldThrowIllegalArgumentException_WhenCnpjIsMissing() {
        merchantUserRequestDTO.setCnpj(null);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        assertThatThrownBy(() -> userService.createUser(merchantUserRequestDTO))
                .isInstanceOf(IllegalArgumentException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void findUserById_ShouldReturnUser_WhenIdExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(commonUser));

        UserResponseDTO result = userService.findUserById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(commonUser.getId());
        assertThat(result.getName()).isEqualTo(commonUser.getName());
        assertThat(result.getUserType()).isEqualTo(commonUser.getUserType());
    }

    @Test
    void findUserById_ShouldThrowUserNotFoundException_WhenIdDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findUserById(1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Usuário não encontrado com ID: ");
    }

    @Test
    void deleteUserById_ShouldDeleteUser_WhenUserHasNoTransactions() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        when(transactionRepository.existsByPayerId(userId)).thenReturn(false);
        when(transactionRepository.existsByPayeeId(userId)).thenReturn(false);
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUserById(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUserById_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteUserById(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Usuário não encontrado com ID: " + userId);

        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void deleteUserById_ShouldThrowUserHasTransactionsException_WhenUserIsPayer() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        when(transactionRepository.existsByPayerId(userId)).thenReturn(true);

        assertThatThrownBy(() -> userService.deleteUserById(userId))
                .isInstanceOf(UserHasTransactionsException.class)
                .hasMessageContaining("Usuário não pode ser removido pois possui transações vinculadas");

        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void deleteUserById_ShouldThrowUserHasTransactionsException_WhenUserIsPayee() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        when(transactionRepository.existsByPayerId(userId)).thenReturn(false);
        when(transactionRepository.existsByPayeeId(userId)).thenReturn(true);

        assertThatThrownBy(() -> userService.deleteUserById(userId))
                .isInstanceOf(UserHasTransactionsException.class)
                .hasMessageContaining("Usuário não pode ser removido pois possui transações vinculadas");

        verify(userRepository, never()).deleteById(any());
    }

}