package lapicito.backend.mapper;

import lapicito.backend.dto.UEPDto;
import lapicito.backend.entity.Usuario_Espacio_Permiso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper (componentModel = "spring")
public interface IUsuario_Espacio_PermisoMapper {

    @Mapping(target = "idUsuario", source = "usuario.id")
    @Mapping(target = "nombre", source = "usuario.nombre")
    @Mapping(target = "apellido", source = "usuario.apellido")
    @Mapping(target = "userName", source = "usuario.userName")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "avatar_url", source = "usuario.avatar_url")
    @Mapping(target = "viewEspacioDto", source = "espacio")
    @Mapping(target = "viewEspacioDto.idEspacio", source = "espacio.id")
    UEPDto toDto (Usuario_Espacio_Permiso usuario_espacio_permiso);


    @Mapping(target = "nombre", source = "usuario.nombre")
    @Mapping(target = "apellido", source = "usuario.apellido")
    @Mapping(target = "userName", source = "usuario.userName")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "avatar_url", source = "usuario.avatar_url")
    default List<UEPDto>toDtoList(List<Usuario_Espacio_Permiso>uep){
        if(uep == null){
            return new ArrayList<>();
        }
        return uep.stream().map(this::toDto).collect(Collectors.toList());
    }

}
