package rct.sistema.backend.gateways.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import rct.sistema.backend.domain.RefreshToken;
import rct.sistema.backend.gateways.entities.RefreshTokenEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {AuthUserMapper.class}
)
public interface RefreshTokenMapper {

    @Mapping(source = "user", target = "user")
    RefreshToken toDomain(RefreshTokenEntity entity);

    @Mapping(source = "user", target = "user")
    RefreshTokenEntity toEntity(RefreshToken domain);
}
