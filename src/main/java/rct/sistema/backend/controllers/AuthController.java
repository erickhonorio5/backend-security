package rct.sistema.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rct.sistema.backend.controllers.dto.request.LoginRequestDTO;
import rct.sistema.backend.controllers.dto.request.RegisterRequestDTO;
import rct.sistema.backend.controllers.dto.response.JwtAuthResponse;
import rct.sistema.backend.usecases.AuthUserUseCase.AuthenticateFacade;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticateFacade authUserFacade;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.info("Recebida requisição de autenticação para o usuário: {}", loginRequest.getUsername());
        var response = authUserFacade.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        log.info("Recebida requisição de registro para o usuário: {}", registerRequest.getUsername());
        var response = authUserFacade.registerUser(registerRequest);
        return ResponseEntity.ok(response);
    }
}