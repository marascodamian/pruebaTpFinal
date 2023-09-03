package lapicito.backend.service;

import lapicito.backend.entity.Categoria;
import lapicito.backend.entity.Usuario;
import lapicito.backend.repository.ICategoriaRepository;
import lapicito.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoriaServiceImpl implements ICategoriaService {

    @Autowired
    private ICategoriaRepository iCategoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Categoria> listarCategoria() {

        return iCategoriaRepository.findAll();
    }


    @Override
    public void saveByAdmin(Integer idUsuario, Categoria categoria) {

    }

    public boolean existById(Integer id) {
        return iCategoriaRepository.existsById(id);
    }


    /*
    public void saveByAdmin(Integer idUsuario, Categoria categoria){​​​​​
        try {​​​​​
            Usuario usuario = usuarioRepository.findById(idUsuario).get();
            if (usuario.getRoles().contains("ROL_ADMIN")) {​​​​​
                iCategoriaRepository.save(categoria);
            }​​​​​
        }​​​​​catch (Exception e){​​​​​
            e.getMessage();
        }​​​​​
    }​​​​​*/


}
