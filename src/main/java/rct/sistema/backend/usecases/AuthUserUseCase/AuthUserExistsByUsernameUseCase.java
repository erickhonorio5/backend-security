package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rct.sistema.backend.exceptions.BusinessException;
import rct.sistema.backend.gateways.AuthUserGateway;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserExistsByUsernameUseCase {

    private final AuthUserGateway authUserGateway;

    public boolean execute(final String username) {
        if (Objects.isNull(username) || username.trim().isEmpty()) {
            log.error("Tentativa de verificar existência com username nulo ou vazio");
            throw new BusinessException("Username não pode ser nulo ou vazio");
        }

        log.debug("Verificando se existe usuário com username: {}", username);
        return authUserGateway.existsByUsername(username);
    }
}
