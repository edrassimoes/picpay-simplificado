package br.com.edras.picpaysimplificado.controller;

import br.com.edras.picpaysimplificado.dto.auth.AuthResponseDTO;
import br.com.edras.picpaysimplificado.dto.auth.LoginRequestDTO;
import br.com.edras.picpaysimplificado.security.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDTO authenticate(@Valid @RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }

}
