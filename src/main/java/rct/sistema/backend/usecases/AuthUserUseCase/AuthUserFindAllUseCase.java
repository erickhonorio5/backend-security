package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.gateways.AuthUserGateway;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserFindAllUseCase {

    private final AuthUserGateway authUserGateway;

    public List<AuthUser> execute() {
        log.info("Buscando todos os usuários");
        var users = authUserGateway.findAll();
        log.info("Total de usuários encontrados: {}", users.size());
        return users;
    }
}
