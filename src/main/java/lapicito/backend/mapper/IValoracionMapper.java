package lapicito.backend.mapper;

import lapicito.backend.dto.EspaciosDto;
import lapicito.backend.dto.ValoracionDto;
import lapicito.backend.dto.ViewValoracionDto;
import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Valoracion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IValoracionMapper {

    @Mapping(target = "usuario.id", source = "id_usuario")
    @Mapping(target = "publicacion.id", source = "id_publicacion")
    Valoracion toEntity (ValoracionDto valoracionDto);

    ValoracionDto toDTO (Valoracion valoracion);

    @Mapping(target = "nombreUsuario", source = "usuario.userName")
    ViewValoracionDto toViewDTO(Valoracion valoracion);


    default List<ViewValoracionDto> toDTOList(List<Valoracion> valoraciones){
        if(valoraciones == null){
            return new ArrayList<>();
        }
        return valoraciones.stream().map(this::toViewDTO).collect(Collectors.toList());
    }

    default List<ValoracionDto> toDTOValoracionList(List<Valoracion> valoraciones){
        if(valoraciones == null){
            return new ArrayList<>();
        }
        return valoraciones.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
