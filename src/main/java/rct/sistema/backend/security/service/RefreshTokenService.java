package rct.sistema.backend.security.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.domain.RefreshToken;
import rct.sistema.backend.exceptions.EntityConflictException;
import rct.sistema.backend.exceptions.InvalidCredentialsException;
import rct.sistema.backend.gateways.AuthUserGateway;
import rct.sistema.backend.gateways.RefreshTokenGateway;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenGateway refreshTokenGateway;
    private final AuthUserGateway authUserGateway;

    @Value("${security.refresh-token.expiration-days:30}")
    private int refreshTokenExpirationDays;

    @Transactional
    public RefreshToken createRefreshToken(String username) {
        log.info("Criando novo refresh token para o usuário: {}", username);

        AuthUser user = authUserGateway.findByUsername(username);
        if (user == null) {
            throw new EntityConflictException("Usuário não encontrado: " + username);
        }

        // Gerar um token único
        String token = UUID.randomUUID().toString();

        // Calcular a data de expiração
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(refreshTokenExpirationDays);

        // Criar o token
        var refreshToken = RefreshToken.builder()
                .user(user)
                .token(token)
                .expiryDate(expiryDate)
                .revoked(false)
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Refresh token criado com sucesso para o usuário: {}, expira em: {}", username, expiryDate);
        return refreshTokenGateway.save(refreshToken);
    }

    public boolean verifyRefreshToken(String token) {
        log.info("Verificando validade do refresh token");
        return refreshTokenGateway.existsByTokenAndNotRevokedAndNotExpired(token, LocalDateTime.now());
    }

    @Transactional
    public RefreshToken findByToken(String token) {
        return refreshTokenGateway.findByToken(token)
                .filter(rt -> !rt.isRevoked() && rt.getExpiryDate().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> {
                    log.warn("Tentativa de uso de refresh token inválido ou expirado");
                    return new InvalidCredentialsException("Refresh token inválido ou expirado");
                });
    }

    @Transactional
    public AuthUser getUserFromRefreshToken(String token) {
        RefreshToken refreshToken = findByToken(token);
        return refreshToken.getUser();
    }

    @Transactional
    public void revokeRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenGateway.findByToken(token)
                .orElseThrow(() -> new InvalidCredentialsException("Refresh token não encontrado"));

        refreshToken.setRevoked(true);
        refreshTokenGateway.save(refreshToken);
        log.info("Refresh token revogado com sucesso");
    }

    @Transactional
    public void revokeAllUserTokens(String username) {
        log.info("Revogando todos os refresh tokens do usuário: {}", username);

        AuthUser user = authUserGateway.findByUsername(username);
        if (user != null) {
            refreshTokenGateway.revokeAllUserTokens(user);
            log.info("Todos os refresh tokens do usuário {} foram revogados", username);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // Executa diariamente à meia-noite
    @Transactional
    public void cleanupExpiredTokens() {
        log.info("Limpando refresh tokens expirados e revogados");
        refreshTokenGateway.deleteAllExpiredAndRevoked(LocalDateTime.now());
        log.info("Limpeza de tokens concluída");
    }
}
