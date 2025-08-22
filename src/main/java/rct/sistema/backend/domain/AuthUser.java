package rct.sistema.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rct.sistema.backend.domain.enums.RoleType;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser {
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private String password;
    private Set<RoleType> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
