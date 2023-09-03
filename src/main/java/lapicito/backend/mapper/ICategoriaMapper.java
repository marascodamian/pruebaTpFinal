package lapicito.backend.mapper;

import lapicito.backend.dto.CategoriasDto;
import lapicito.backend.dto.FullCategoriaDto;
import lapicito.backend.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper (componentModel = "spring")
public interface ICategoriaMapper {

    Categoria toEntity (CategoriasDto categoriasDto);
    CategoriasDto toDTO (Categoria categoria);

    FullCategoriaDto toFullDto (Categoria categoria);

    default List<FullCategoriaDto> toDTOList(List<Categoria> categorias){
        if(categorias == null){
            return new ArrayList<>();
        }
        return categorias.stream().map(this::toFullDto).collect(Collectors.toList());
    }

}
