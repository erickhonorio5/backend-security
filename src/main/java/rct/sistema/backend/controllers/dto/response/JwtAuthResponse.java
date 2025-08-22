package rct.sistema.backend.controllers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rct.sistema.backend.domain.enums.RoleType;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private String fullname;
    private Set<RoleType> roles;
}
