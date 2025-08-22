package rct.sistema.backend.gateways;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.domain.RefreshToken;
import rct.sistema.backend.exceptions.UserNotFoundException;
import rct.sistema.backend.gateways.entities.AuthUserEntity;
import rct.sistema.backend.gateways.mapper.RefreshTokenMapper;
import rct.sistema.backend.repositories.AuthUserRepository;
import rct.sistema.backend.repositories.RefreshTokenRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenGateway {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthUserRepository authUserRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenMapper.toDomain(
                refreshTokenRepository.save(refreshTokenMapper.toEntity(refreshToken))
        );
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(refreshTokenMapper::toDomain);
    }

    public List<RefreshToken> findAllByUser(AuthUser user) {
        AuthUserEntity userEntity = authUserRepository.findById(user.getId())
                .orElseThrow(UserNotFoundException::new);
                
        return refreshTokenRepository.findAllByUser(userEntity)
                .stream()
                .map(refreshTokenMapper::toDomain)
                .toList();
    }

    public boolean existsByTokenAndNotRevokedAndNotExpired(String token, LocalDateTime now) {
        return refreshTokenRepository.existsByTokenAndRevokedFalseAndExpiryDateAfter(token, now);
    }

    @Transactional
    public void deleteAllExpiredAndRevoked(LocalDateTime now) {
        refreshTokenRepository.deleteAllExpiredAndRevoked(now);
    }

    @Transactional
    public void revokeAllUserTokens(AuthUser user) {
        authUserRepository.findByUsername(user.getUsername())
                .ifPresent(refreshTokenRepository::revokeAllUserTokens);
    }
}
