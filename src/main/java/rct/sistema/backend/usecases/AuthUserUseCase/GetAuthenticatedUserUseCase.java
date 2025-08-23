package rct.sistema.backend.usecases.AuthUserUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rct.sistema.backend.controllers.dto.response.UserInfoResponse;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.exceptions.UnauthorizedException;
import rct.sistema.backend.exceptions.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAuthenticatedUserUseCase {

    private final AuthUserUseCase authUserUseCase;

    public UserInfoResponse execute() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
            "anonymousUser".equals(authentication.getPrincipal())) {
            log.warn("Tentativa de acesso não autenticado ao endpoint /me");
            throw new UnauthorizedException("Usuário não está autenticado");
        }

        String username = authentication.getName();
        log.info("Buscando informações do usuário autenticado: {}", username);

        try {
            var user = authUserUseCase.findByUsername(username);

            log.info("Retornando informações do usuário autenticado: {}", username);

            return UserInfoResponse.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .fullname(user.getFullname())
                    .roles(user.getRoles())
                    .build();
        } catch (UserNotFoundException e) {
            log.error("Usuário com token válido não encontrado no banco de dados: {}", username);
            throw new UnauthorizedException("Token válido, mas usuário não encontrado");
        }
    }
}