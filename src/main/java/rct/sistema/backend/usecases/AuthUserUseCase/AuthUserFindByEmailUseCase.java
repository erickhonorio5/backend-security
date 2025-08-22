package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.gateways.AuthUserGateway;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserFindByEmailUseCase {

    private final AuthUserGateway authUserGateway;

    public AuthUser execute(final String email) {
        log.info("Buscando usuário pelo email: {}", email);
        var user = authUserGateway.findByEmail(email);
        log.info("Usuário encontrado com email: {}", user.getUsername());
        return user;
    }
}
