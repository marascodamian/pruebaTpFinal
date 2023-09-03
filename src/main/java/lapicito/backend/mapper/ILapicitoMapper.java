package lapicito.backend.mapper;

import lapicito.backend.dto.EspaciosDto;
import lapicito.backend.dto.ViewLapicitoDto;
import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Lapicito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = ICategoriaMapper.class)
public interface ILapicitoMapper {

    Lapicito toEntity(ViewLapicitoDto viewLapicitoDto);


    ViewLapicitoDto toViewDto(Lapicito lapicito);

    default List<ViewLapicitoDto> toDTOList(List<Lapicito> lapicitos){
        if(lapicitos == null){
            return new ArrayList<>();
        }
        return lapicitos.stream().map(this::toViewDto).collect(Collectors.toList());
    }
}
