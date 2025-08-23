package rct.sistema.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Usuário não autenticado ou sem permissão para acessar este recurso");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
