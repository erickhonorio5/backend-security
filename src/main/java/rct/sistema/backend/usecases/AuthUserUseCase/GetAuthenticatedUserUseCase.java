package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rct.sistema.backend.controllers.dto.response.UserInfoResponse;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.exceptions.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAuthenticatedUserUseCase {

    private final AuthUserUseCase authUserUseCase;

    public UserInfoResponse execute() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Buscando informações do usuário autenticado: {}", username);

        var user = authUserUseCase.findByUsername(username);

        if (user == null) {
            log.error("Usuário autenticado não encontrado no banco de dados: {}", username);
            throw new UserNotFoundException();
        }

        log.info("Retornando informações do usuário autenticado: {}", username);

        return UserInfoResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .fullname(user.getFullname())
                .roles(user.getRoles())
                .build();
    }
}