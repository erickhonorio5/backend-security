package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.gateways.AuthUserGateway;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserFindByUsernameUseCase {

    private final AuthUserGateway authUserGateway;

    public AuthUser execute(final String username) {
        log.info("Buscando usuário pelo nome de usuário: {}", username);
        var user = authUserGateway.findByUsername(username);
        log.info("Usuário encontrado com username: {}", user.getUsername());
        return user;
    }
}
