package lapicito.backend.service;

import lapicito.backend.dto.ArrayCategoriasDto;
import lapicito.backend.dto.PublicacionDto;
import lapicito.backend.dto.ViewPublicacionConValoracionDto;
import lapicito.backend.dto.ViewPublicacionDto;
import lapicito.backend.entity.Publicacion;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface IPublicacionService {
    void save(PublicacionDto publicacionDto, MultipartFile publicacion_file);
    List<ViewPublicacionDto> getViewListByIdUsuario(int id);
    List<ViewPublicacionDto> getViewListByIdEspacio(int id);
    boolean existById(int id);
    ViewPublicacionConValoracionDto getListByIdPublicacion(int id);
    List<ViewPublicacionDto> getListByArrayCategorias(ArrayCategoriasDto ac);
    void updatePublicacion(PublicacionDto publicacionDto, MultipartFile publicacion_file, int id);
    void deletePublicacion(int id);

    Publicacion findById(int id_publicacion);
}
