package lapicito.backend.mapper;

import lapicito.backend.dto.UEPDto;
import lapicito.backend.dto.UsuarioPerfilDto;
import lapicito.backend.entity.Usuario;
import lapicito.backend.entity.Usuario_Espacio_Permiso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper (componentModel = "spring")
public interface IUsuarioMapper {

    Usuario toEntity(UsuarioPerfilDto usuarioPerfilDto);

    @Mapping(target = "mp_user_id", source = "mpUser.id")
    UsuarioPerfilDto toDTO(Usuario usuario);


    default List<UsuarioPerfilDto> toDtoList(List<Usuario>u){
        if(u == null){
            return new ArrayList<>();
        }
        return u.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
