package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rct.sistema.backend.controllers.dto.request.LoginRequestDTO;
import rct.sistema.backend.controllers.dto.request.RegisterRequestDTO;
import rct.sistema.backend.controllers.dto.response.JwtAuthResponse;

@Service
@RequiredArgsConstructor
public class AuthenticateFacade {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final AuthRegisterUserUseCase registerUserUseCase;

    public JwtAuthResponse authenticateUser(LoginRequestDTO loginRequest) {
        return authenticateUserUseCase.execute(loginRequest);
    }

    public JwtAuthResponse registerUser(RegisterRequestDTO registerRequest) {
        return registerUserUseCase.execute(registerRequest);
    }
}
