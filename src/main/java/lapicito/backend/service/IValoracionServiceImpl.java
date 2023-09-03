package lapicito.backend.service;

import lapicito.backend.dto.ViewPublicacionDto;
import lapicito.backend.entity.Valoracion;
import lapicito.backend.mapper.IPublicacionMapper;
import lapicito.backend.repository.IValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class IValoracionServiceImpl implements IValoracionService {

    @Autowired
    IValoracionRepository valoracionRepository;

    @Autowired
    IPublicacionMapper publicacionMapper;

    @Autowired
    IPublicacionService publicacionService;


    public void save(Valoracion valoracion){

        valoracion.setFecha(Calendar.getInstance());
        valoracionRepository.save(valoracion);
    }

    public boolean existsValoracionByUsuarioIdAndPublicacionId(int id_U, int id_P){
        return valoracionRepository.existsValoracionByUsuarioIdAndPublicacionId(id_U,id_P);
    }

    public List<Valoracion> getValoracionByPublicacionId(int id){
        return valoracionRepository.getValoracionByPublicacionId(id);
    }

    public double getPromedioValoraciones(int id){
        List<Valoracion> valoraciones = this.getValoracionByPublicacionId(id);
        int sumaEstrellas = 0;
        double promedioValoracion;

        for (Valoracion item: valoraciones
             ) {
            sumaEstrellas += item.getCantidad_estrellas();
        }

        if(sumaEstrellas == 0){
            promedioValoracion = 0.0;
        }else{
            promedioValoracion = (double) sumaEstrellas/valoraciones.size();
        }
        return promedioValoracion;
    }

    public double getPromedioValoracionesByEspacio(int id){

        List<ViewPublicacionDto> publicaciones = publicacionService.getViewListByIdEspacio(id);
        double sumaPromedioValoracion = 0.0;
        int sumaValoraciones = 0;

        for (ViewPublicacionDto item: publicaciones) {

            if(item.getPromedioValoracion() == 0.0){
            }else{
                sumaPromedioValoracion += item.getPromedioValoracion();
                sumaValoraciones++;
            }
        }
        if(sumaPromedioValoracion == 0.0){

            return 0.0;
        }else{
            return sumaPromedioValoracion/sumaValoraciones;
        }
    }
}
