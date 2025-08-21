package rct.sistema.backend.exceptions;

public class NoChangesException extends RuntimeException {
    public NoChangesException() {
        super("Nenhuma alteração foi detectada. Os dados enviados são iguais aos já existentes.");
    }
}

