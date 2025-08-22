package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rct.sistema.backend.domain.AuthUser;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserFacade {

    private final AuthUserSaveUseCase saveUseCase;
    private final AuthUserFindByIdUseCase findByIdUseCase;
    private final AuthUserFindByUsernameUseCase findByUsernameUseCase;
    private final AuthUserFindByEmailUseCase findByEmailUseCase;
    private final AuthUserFindAllUseCase findAllUseCase;
    private final AuthUserDeleteByIdUseCase deleteByIdUseCase;
    private final AuthUserExistsByUsernameUseCase existsByUsernameUseCase;
    private final AuthUserExistsByEmailUseCase existsByEmailUseCase;

    public AuthUser save(final AuthUser authUser) {
        return saveUseCase.execute(authUser);
    }

    public AuthUser findById(final Long id) {
        return findByIdUseCase.execute(id);
    }

    public AuthUser findByUsername(final String username) {
        return findByUsernameUseCase.execute(username);
    }

    public AuthUser findByEmail(final String email) {
        return findByEmailUseCase.execute(email);
    }

    public List<AuthUser> findAll() {
        return findAllUseCase.execute();
    }

    public void deleteById(final Long id) {
        deleteByIdUseCase.execute(id);
    }

    public boolean existsByUsername(final String username) {
        return existsByUsernameUseCase.execute(username);
    }

    public boolean existsByEmail(final String email) {
        return existsByEmailUseCase.execute(email);
    }
}
