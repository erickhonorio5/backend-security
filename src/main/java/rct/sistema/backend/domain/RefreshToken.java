package rct.sistema.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    private Long id;
    private String token;
    private AuthUser user;
    private LocalDateTime expiryDate;
    private LocalDateTime createdAt;
    private boolean revoked;
}
