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
public class AuthUserDeleteByIdUseCase {

    private final AuthUserGateway authUserGateway;
    private final AuthUserFindByIdUseCase findByIdUseCase;

    public void execute(final Long id) {
        if (Objects.isNull(id)) {
            log.error("Tentativa de excluir usuário com ID nulo");
            throw new BusinessException("ID do usuário não pode ser nulo");
        }

        log.info("Iniciando processo de exclusão do usuário com ID: {}", id);
        findByIdUseCase.execute(id);

        authUserGateway.deleteById(id);
        log.info("Usuário com ID {} foi excluído com sucesso", id);
    }
}
