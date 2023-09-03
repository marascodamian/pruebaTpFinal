package lapicito.backend.mapper;

import lapicito.backend.dto.ViewPublicacionConValoracionDto;
import lapicito.backend.dto.ViewPublicacionDto;
import lapicito.backend.entity.Publicacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper (componentModel = "spring")
public interface IPublicacionMapper {

    Publicacion toEntity (ViewPublicacionDto viewPublicacionDto);

    @Mapping(target = "idPublicacion", source = "publicacion.id")
    ViewPublicacionDto toDto (Publicacion publicacion);

    @Mapping(target = "idPublicacion", source = "publicacion.id")
    @Mapping(target = "espacio.idEspacio", source = "espacio.id")
    @Mapping(target = "usuario.mp_user_id", source = "usuario.mpUser.id")
    ViewPublicacionConValoracionDto toConValoracionDto (Publicacion publicacion);

    default List<ViewPublicacionDto> toDTOList(List<Publicacion> publicaciones){
        if(publicaciones == null){
            return new ArrayList<>();
        }
        return publicaciones.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<ViewPublicacionConValoracionDto> toDTOConValoracionList(List<Publicacion> publicaciones){
        if(publicaciones == null){
            return new ArrayList<>();
        }
        return publicaciones.stream().map(this::toConValoracionDto).collect(Collectors.toList());
    }


}
