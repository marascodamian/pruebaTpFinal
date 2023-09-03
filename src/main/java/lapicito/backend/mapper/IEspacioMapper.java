package lapicito.backend.mapper;

import lapicito.backend.dto.EspaciosDto;
import lapicito.backend.dto.ViewEspacioDto;
import lapicito.backend.entity.Espacio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper (componentModel = "spring", uses = ICategoriaMapper.class)
public interface IEspacioMapper {

    Espacio toEntity(EspaciosDto espaciosDto);

    @Mapping(target = "id", source = "viewEspacioDto.idEspacio")
    Espacio toViewEntity(ViewEspacioDto viewEspacioDto);

    @Mapping(target = "id_categoria", source = "categoria.idCategoria")

    EspaciosDto toDTO(Espacio espacio);

    @Mapping(target = "idEspacio", source = "espacio.id")
    ViewEspacioDto toViewDTO(Espacio espacio);

    default List<EspaciosDto> toDTOList(List<Espacio> espacios){
        if(espacios == null){
            return new ArrayList<>();
        }
        return espacios.stream().map(this::toDTO).collect(Collectors.toList());
    }

    default List<ViewEspacioDto> toViewDTOList(List<Espacio> espacios){
        if(espacios == null){
            return new ArrayList<>();
        }
        return espacios.stream().map(this::toViewDTO).collect(Collectors.toList());
    }
}
