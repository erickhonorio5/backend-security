package rct.sistema.backend.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    private static final String MSG = "Credenciais inválidas!";

    public InvalidCredentialsException() {
        super(MSG);
    }
}
