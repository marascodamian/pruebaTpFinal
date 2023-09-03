package lapicito.backend.service;

import lapicito.backend.dto.*;
import lapicito.backend.entity.Publicacion;
import lapicito.backend.entity.Usuario;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface IRankingService {

    List<Usuario> getUsuariosMasDonados(ArrayCategoriasDto arrayCategoriasDto);
    List<ViewPublicacionConValoracionDto>getPublicacionesMasValoradas(ArrayCategoriasDto ac);
    List<ViewPublicacionConValoracionDto>getPublicacionesMasDescargadas(ArrayCategoriasDto ac);
    List<ViewEspacioDto> getEspaciosMasSeguidos(ArrayCategoriasDto ac);

}
