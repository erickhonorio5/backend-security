package rct.sistema.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rct.sistema.backend.controllers.dto.request.LoginRequestDTO;
import rct.sistema.backend.controllers.dto.request.RefreshTokenRequestDTO;
import rct.sistema.backend.controllers.dto.request.RegisterRequestDTO;
import rct.sistema.backend.controllers.dto.response.JwtAuthResponse;
import rct.sistema.backend.controllers.dto.response.UserInfoResponse;
import rct.sistema.backend.usecases.AuthUserUseCase.AuthenticateUseCase;
import rct.sistema.backend.usecases.AuthUserUseCase.GetAuthenticatedUserUseCase;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticateUseCase authenticateUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.info("Recebida requisição de autenticação para o usuário: {}", loginRequest.getUsername());
        var response = authenticateUseCase.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        log.info("Recebida requisição de registro para o usuário: {}", registerRequest.getUsername());
        var response = authenticateUseCase.registerUser(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
        log.info("Recebida requisição para refresh token");
        var response = authenticateUseCase.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
        log.info("Recebida requisição de logout");
        authenticateUseCase.logout(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getCurrentUser() {
        log.info("Recebida requisição para obter informações do usuário autenticado");
        var userInfo = getAuthenticatedUserUseCase.execute();
        return ResponseEntity.ok(userInfo);
    }
}