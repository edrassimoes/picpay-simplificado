package br.com.edras.picpaysimplificado.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.com.edras.picpaysimplificado.dto.auth.AuthResponseDTO;
import br.com.edras.picpaysimplificado.dto.auth.LoginRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginSuccessfullyAndReturnToken() {
        LoginRequestDTO dto = new LoginRequestDTO(
                "user@email.com",
                "password123"
        );

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(jwtService.generateToken(authentication))
                .thenReturn("jwt-token");

        AuthResponseDTO response = authService.login(dto);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(authentication);
    }

    @Test
    void shouldThrowExceptionWhenAuthenticationFails() {
        LoginRequestDTO dto = new LoginRequestDTO(
                "user@email.com",
                "wrong-password"
        );

        when(authenticationManager.authenticate(any()))
                .thenThrow(new RuntimeException("Invalid credentials"));

        assertThrows(RuntimeException.class, () -> authService.login(dto));

        verify(authenticationManager).authenticate(any());
        verify(jwtService, never()).generateToken(any());
    }

}