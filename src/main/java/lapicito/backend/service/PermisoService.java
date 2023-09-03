package lapicito.backend.service;

import lapicito.backend.entity.Permiso;
import lapicito.backend.enums.PermisoNombre;
import lapicito.backend.repository.IPermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PermisoService {

    @Autowired
    IPermisoRepository permisoRepository;

    public Permiso getPermisoByNombre(PermisoNombre permisoNombre){
        return permisoRepository.findByPermisoNombre(permisoNombre);
    }

}
