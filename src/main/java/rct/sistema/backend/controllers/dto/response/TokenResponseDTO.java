package rct.sistema.backend.controllers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDTO {

    private String token;
    private String type = "Bearer";
    private String username;

    public TokenResponseDTO(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
