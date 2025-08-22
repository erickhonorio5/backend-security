package rct.sistema.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rct.sistema.backend.gateways.entities.AuthUserEntity;
import rct.sistema.backend.gateways.entities.RefreshTokenEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    List<RefreshTokenEntity> findAllByUser(AuthUserEntity user);

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity rt WHERE rt.expiryDate < ?1 OR rt.revoked = true")
    void deleteAllExpiredAndRevoked(LocalDateTime now);

    @Modifying
    @Query("UPDATE RefreshTokenEntity rt SET rt.revoked = true WHERE rt.user = ?1")
    void revokeAllUserTokens(AuthUserEntity user);

    boolean existsByTokenAndRevokedFalseAndExpiryDateAfter(String token, LocalDateTime now);
}
