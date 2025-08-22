package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.exceptions.BusinessException;
import rct.sistema.backend.gateways.AuthUserGateway;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserUseCase {

    private final AuthUserGateway authUserGateway;

    public AuthUser save(final AuthUser authUser) {
        if (Objects.isNull(authUser)) {
            log.error("Tentativa de salvar usuário nulo");
            throw new BusinessException("Usuário não pode ser nulo");
        }

        log.info("Salvando usuário: {}", authUser.getUsername());
        return authUserGateway.save(authUser);
    }

    public AuthUser findById(final Long id) {
        log.info("Buscando usuário pelo ID: {}", id);
        var user = authUserGateway.findById(id);
        log.info("Usuário encontrado: {}", user.getUsername());
        return user;
    }

    public AuthUser findByUsername(final String username) {
        log.info("Buscando usuário pelo nome de usuário: {}", username);
        var user = authUserGateway.findByUsername(username);
        log.info("Usuário encontrado com username: {}", user.getUsername());
        return user;
    }

    public AuthUser findByEmail(final String email) {
        log.info("Buscando usuário pelo e-mail: {}", email);
        var user = authUserGateway.findByEmail(email);
        log.info("Usuário encontrado com e-mail: {}", user.getEmail());
        return user;
    }

    public List<AuthUser> findAll() {
        log.info("Buscando todos os usuários");
        var users = authUserGateway.findAll();
        log.info("Total de usuários encontrados: {}", users.size());
        return users;
    }

    public void deleteById(final Long id) {
        log.info("Deletando usuário com ID: {}", id);
        findById(id);
        authUserGateway.deleteById(id);
        log.info("Usuário com ID {} deletado com sucesso", id);
    }

    public boolean existsByUsername(final String username) {
        log.info("Verificando se existe usuário com username: {}", username);
        boolean exists = authUserGateway.existsByUsername(username);
        log.info("Resultado da verificação para username {}: {}", username, exists);
        return exists;
    }

    public boolean existsByEmail(final String email) {
        log.info("Verificando se existe usuário com email: {}", email);
        boolean exists = authUserGateway.existsByEmail(email);
        log.info("Resultado da verificação para email {}: {}", email, exists);
        return exists;
    }
}
