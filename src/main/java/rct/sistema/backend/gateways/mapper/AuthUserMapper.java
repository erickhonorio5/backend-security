package rct.sistema.backend.gateways.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import rct.sistema.backend.domain.AuthUser;
import rct.sistema.backend.gateways.entities.AuthUserEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AuthUserMapper {

    AuthUser toDomain (AuthUserEntity entity);

    AuthUserEntity toEntity (AuthUser model);
}