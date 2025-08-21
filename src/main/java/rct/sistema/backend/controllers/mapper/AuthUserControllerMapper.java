package rct.sistema.backend.controllers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rct.sistema.backend.controllers.dto.request.AuthUserRequestDTO;
import rct.sistema.backend.domain.AuthUser;

@Mapper(componentModel = "spring")
public interface AuthUserControllerMapper {

    @Mapping(target = "roles", expression = "java(Set.of(RoleType.USER))")
    @Mapping(target = "isActive", constant = "true")
    AuthUser toAuthUser(AuthUserRequestDTO requestDTO);
}
