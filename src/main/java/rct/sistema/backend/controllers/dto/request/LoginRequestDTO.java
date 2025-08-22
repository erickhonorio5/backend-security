package rct.sistema.backend.controllers.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "O nome de usuário não pode estar em branco")
    private String username;

    @NotBlank(message = "A senha não pode estar em branco")
    private String password;
}
