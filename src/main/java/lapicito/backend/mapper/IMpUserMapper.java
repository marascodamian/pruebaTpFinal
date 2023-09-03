package lapicito.backend.mapper;

import lapicito.backend.dto.MpUserDto;
import lapicito.backend.entity.MPUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = IUsuarioMapper.class)
public interface IMpUserMapper {

    @Mapping(target = "id", source = "idUsuario")
    MPUser toEntity(MpUserDto mpUserDto);
}
