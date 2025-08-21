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

        log.info("Iniciando processo de salvamento do usuário: {}", authUser.getUsername());
        var savedUser = authUserGateway.save(authUser);
        log.info("Usuário salvo com sucesso: ID {}", savedUser.getId());
        return savedUser;
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
        log.info("Buscando usuário pelo email: {}", email);
        var user = authUserGateway.findByEmail(email);
        log.info("Usuário encontrado com email: {}", user.getUsername());
        return user;
    }

    public List<AuthUser> findAll() {
        log.info("Buscando todos os usuários");
        var users = authUserGateway.findAll();
        log.info("Total de usuários encontrados: {}", users.size());
        return users;
    }

    public void deleteById(final Long id) {
        if (Objects.isNull(id)) {
            log.error("Tentativa de excluir usuário com ID nulo");
            throw new BusinessException("ID do usuário não pode ser nulo");
        }

        log.info("Iniciando processo de exclusão do usuário com ID: {}", id);
        findById(id);

        authUserGateway.deleteById(id);
        log.info("Usuário com ID {} foi excluído com sucesso", id);
    }

    public boolean existsByUsername(final String username) {
        if (Objects.isNull(username) || username.trim().isEmpty()) {
            log.error("Tentativa de verificar existência com username nulo ou vazio");
            throw new BusinessException("Username não pode ser nulo ou vazio");
        }

        log.debug("Verificando se existe usuário com username: {}", username);
        return authUserGateway.existsByUsername(username);
    }

    public boolean existsByEmail(final String email) {
        if (Objects.isNull(email) || email.trim().isEmpty()) {
            log.error("Tentativa de verificar existência com email nulo ou vazio");
            throw new BusinessException("Email não pode ser nulo ou vazio");
        }

        log.debug("Verificando se existe usuário com email: {}", email);
        return authUserGateway.existsByEmail(email);
    }
}
