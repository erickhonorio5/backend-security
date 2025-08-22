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
public class AuthUserExistsByEmailUseCase {

    private final AuthUserGateway authUserGateway;

    public boolean execute(final String email) {
        if (Objects.isNull(email) || email.trim().isEmpty()) {
            log.error("Tentativa de verificar existência com email nulo ou vazio");
            throw new BusinessException("Email não pode ser nulo ou vazio");
        }

        log.debug("Verificando se existe usuário com email: {}", email);
        return authUserGateway.existsByEmail(email);
    }
}
