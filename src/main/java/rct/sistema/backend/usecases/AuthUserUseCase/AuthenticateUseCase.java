package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rct.sistema.backend.controllers.dto.request.LoginRequestDTO;
import rct.sistema.backend.controllers.dto.request.RefreshTokenRequestDTO;
import rct.sistema.backend.controllers.dto.request.RegisterRequestDTO;
import rct.sistema.backend.controllers.dto.response.JwtAuthResponse;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.domain.RefreshToken;
import rct.sistema.backend.domain.enums.RoleType;
import rct.sistema.backend.exceptions.EntityConflictException;
import rct.sistema.backend.exceptions.InvalidCredentialsException;
import rct.sistema.backend.gateways.AuthUserGateway;
import rct.sistema.backend.security.jwt.JwtTokenProvider;
import rct.sistema.backend.security.service.RefreshTokenService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticateUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUserGateway authUserGateway;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public JwtAuthResponse authenticateUser(LoginRequestDTO loginRequest) {
        log.info("Autenticando usuário: {}", loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        LocalDateTime expiryDateTime = jwtTokenProvider.getExpirationDateFromNow();

        // Criar refresh token usando o modelo de domínio
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());

        log.info("Usuário {} autenticado com sucesso", loginRequest.getUsername());

        return JwtAuthResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken.getToken())
                .type("Bearer")
                .expireAt(expiryDateTime)
                .build();
    }

    @Transactional
    public JwtAuthResponse refreshToken(RefreshTokenRequestDTO refreshTokenRequest) {
        log.info("Processando solicitação de refresh token");

        String requestRefreshToken = refreshTokenRequest.getRefreshToken();

        if (!refreshTokenService.verifyRefreshToken(requestRefreshToken)) {
            log.warn("Refresh token inválido ou expirado");
            throw new InvalidCredentialsException("Refresh token inválido ou expirado");
        }

        AuthUser user = refreshTokenService.getUserFromRefreshToken(requestRefreshToken);
        log.info("Usuário identificado pelo refresh token: {}", user.getUsername());

        String jwt = jwtTokenProvider.generateTokenForUser(user.getUsername());
        LocalDateTime expiryDateTime = jwtTokenProvider.getExpirationDateFromNow();

        refreshTokenService.revokeRefreshToken(requestRefreshToken);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        log.info("Token atualizado com sucesso para o usuário: {}", user.getUsername());

        return JwtAuthResponse.builder()
                .token(jwt)
                .refreshToken(newRefreshToken.getToken())
                .type("Bearer")
                .expireAt(expiryDateTime)
                .build();
    }

    @Transactional
    public JwtAuthResponse registerUser(RegisterRequestDTO registerRequest) {
        log.info("Iniciando registro de novo usuário: {}", registerRequest.getUsername());

        if (authUserGateway.existsByUsername(registerRequest.getUsername())) {
            log.warn("Tentativa de registro com username já existente: {}", registerRequest.getUsername());
            throw new EntityConflictException("Nome de usuário já está em uso");
        }

        if (authUserGateway.existsByEmail(registerRequest.getEmail())) {
            log.warn("Tentativa de registro com email já existente: {}", registerRequest.getEmail());
            throw new EntityConflictException("Email já está em uso");
        }

        var user = AuthUser.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .fullname(registerRequest.getFullname())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(new HashSet<>(Collections.singleton(RoleType.USER)))
                .build();

        log.info("Salvando novo usuário: {}", user.getUsername());
        authUserGateway.save(user);
        log.info("Usuário registrado com sucesso: {}", user.getUsername());

        return authenticateUser(new LoginRequestDTO(registerRequest.getUsername(), registerRequest.getPassword()));
    }

    @Transactional
    public void logout(String refreshToken) {
        log.info("Processando logout");
        if (refreshToken != null && !refreshToken.isEmpty()) {
            try {
                refreshTokenService.revokeRefreshToken(refreshToken);
                log.info("Refresh token revogado com sucesso durante logout");
            } catch (Exception e) {
                log.warn("Falha ao revogar refresh token durante logout: {}", e.getMessage());
            }
        }
        SecurityContextHolder.clearContext();
        log.info("Logout realizado com sucesso");
    }
}
