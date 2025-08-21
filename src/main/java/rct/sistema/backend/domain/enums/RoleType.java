package rct.sistema.backend.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rct.sistema.backend.exceptions.EntityConflictException;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum RoleType {
    ADMIN("Administrador com acesso total ao sistema"),
    MANAGER("Gerente com acesso a recursos de gestão"),
    USER("Usuário regular com acesso padrão"),
    READ_ONLY("Usuário com acesso somente leitura");

    private final String roleType;

    public static RoleType fromString(String role) {
        return Arrays.stream(RoleType.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findAny()
                .orElseThrow(() -> new EntityConflictException("Nenhum enum encontrado com o nome: " + role));
    }
}