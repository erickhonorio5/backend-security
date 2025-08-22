package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.domain.enums.RoleType;
import rct.sistema.backend.exceptions.BusinessException;
import rct.sistema.backend.gateways.AuthUserGateway;

import java.util.HashSet;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserSaveUseCase {

    private final AuthUserGateway authUserGateway;

    public AuthUser execute(final AuthUser authUser) {
        if (Objects.isNull(authUser)) {
            log.error("Tentativa de salvar usuário nulo");
            throw new BusinessException("Usuário não pode ser nulo");
        }

        return authUserGateway.save(authUser);
    }
}
