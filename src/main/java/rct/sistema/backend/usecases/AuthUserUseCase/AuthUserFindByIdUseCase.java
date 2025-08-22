package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.gateways.AuthUserGateway;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserFindByIdUseCase {

    private final AuthUserGateway authUserGateway;

    public AuthUser execute(final Long id) {
        log.info("Buscando usuário pelo ID: {}", id);
        var user = authUserGateway.findById(id);
        log.info("Usuário encontrado: {}", user.getUsername());
        return user;
    }
}
