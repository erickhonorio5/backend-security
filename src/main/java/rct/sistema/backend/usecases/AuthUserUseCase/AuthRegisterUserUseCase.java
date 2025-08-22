package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rct.sistema.backend.controllers.dto.request.LoginRequestDTO;
import rct.sistema.backend.controllers.dto.request.RegisterRequestDTO;
import rct.sistema.backend.controllers.dto.response.JwtAuthResponse;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.domain.enums.RoleType;
import rct.sistema.backend.exceptions.EntityConflictException;
import rct.sistema.backend.gateways.AuthUserGateway;

import java.util.Collections;
import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthRegisterUserUseCase {

    private final AuthUserGateway authUserGateway;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    public JwtAuthResponse execute(RegisterRequestDTO registerRequest) {
        if (authUserGateway.existsByUsername(registerRequest.getUsername())) {
            throw new EntityConflictException("Nome de usuário já está em uso");
        }

        if (authUserGateway.existsByEmail(registerRequest.getEmail())) {
            throw new EntityConflictException("Email já está em uso");
        }

        AuthUser user = AuthUser.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .fullname(registerRequest.getFullname())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(new HashSet<>(Collections.singleton(RoleType.USER)))
                .build();

        log.info("Registrando novo usuário: {}", user.getUsername());

        AuthUser savedUser = authUserGateway.save(user);

        log.info("Usuário salvo com sucesso: {}", savedUser.getUsername());

        return authenticateUserUseCase.execute(
                new LoginRequestDTO(registerRequest.getUsername(), registerRequest.getPassword())
        );
    }
}
