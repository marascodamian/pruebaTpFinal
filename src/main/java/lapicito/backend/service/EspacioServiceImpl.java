package lapicito.backend.service;


import lapicito.backend.dto.ArrayCategoriasDto;
import lapicito.backend.dto.EspaciosDto;
import lapicito.backend.dto.ViewEspacioDto;
import lapicito.backend.entity.Categoria;
import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Usuario_Espacio_Permiso;
import lapicito.backend.mapper.IEspacioMapper;
import lapicito.backend.mapper.IUsuarioMapper;
import lapicito.backend.repository.ICategoriaRepository;
import lapicito.backend.repository.IEspacioRepository;
import lapicito.backend.repository.IUsuario_Espacio_PermisoRepository;
import lapicito.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class EspacioServiceImpl implements IEspacioService {

    @Autowired
    IEspacioRepository espacioRepository;

    @Autowired
    IUsuario_Espacio_PermisoRepository usuario_espacio_permisoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ICategoriaRepository iCategoriaRepository;

    @Autowired
    IEspacioMapper espacioMapper;

    @Autowired
    IValoracionService valoracionService;

    @Autowired
    IUsuarioMapper usuarioMapper;

    @Override
    public void saveByUsuario(Espacio espacio){
        espacioRepository.save(espacio);
    }

    @Override
    public void save(Espacio espacio) {

    }

    @Override
    public Optional<Espacio> getById(int id) {
        return espacioRepository.findById(id);
    }

    @Override
    public ViewEspacioDto getDtoById(int id){

        ViewEspacioDto espacio = espacioMapper.toViewDTO(this.getById(id).get());
        espacio.setPromedioValoraciones(valoracionService.getPromedioValoracionesByEspacio(id));
        espacio.setUsuarioPerfilDto(usuarioMapper.toDTO(usuario_espacio_permisoRepository.findUEPByIdEspacio(id).getUsuario()));
        espacio.setCantidadMiembrosEspacio(usuario_espacio_permisoRepository.findAllByEspacioId(id).size()-1);
        return espacio;
    }

    @Override
    public List<ViewEspacioDto> search(String value, ArrayCategoriasDto ac) {
        List<ViewEspacioDto> espacios = getListByArrayCategorias(ac);
        List<ViewEspacioDto> espacioSearch = new ArrayList<>();

        for (ViewEspacioDto e:
                espacios) {
            espacioSearch.addAll(espacioMapper.toViewDTOList(espacioRepository.search(value,espacioMapper.toViewEntity(e).getId())));
        }
        for (ViewEspacioDto es: espacioSearch
             ) {
            es.setUsuarioPerfilDto(usuarioMapper.toDTO(usuario_espacio_permisoRepository.findUEPByIdEspacio(es.getIdEspacio()).getUsuario()));
            es.setPromedioValoraciones(valoracionService.getPromedioValoracionesByEspacio(es.getIdEspacio()));
            es.setCantidadMiembrosEspacio(usuario_espacio_permisoRepository.findAllByEspacioId(es.getIdEspacio()).size()-1);
        }
        return espacioSearch;
    }


    @Override
    public boolean existsById(int id) {
        return espacioRepository.existsById(id);
    }

    @Override
    public void update(Espacio espacio) {

        Espacio oldEspacio = espacioRepository.findById(espacio.getId()).get();
        oldEspacio.setDescripcion(espacio.getDescripcion());
        oldEspacio.setCategoria(espacio.getCategoria());
        oldEspacio.setPortada_url(espacio.getPortada_url());
    }

    @Override
    public void delete(int id){
        usuario_espacio_permisoRepository.deleteByEspacioId(id);
        espacioRepository.deleteById(id);
        //Usuario_Espacio_Permiso uep = usuario_espacio_permisoRepository.findByEspacioId(id);
        //usuario_espacio_permisoRepository.deleteById(uep.getEspacio().getId());

    }


    public List<ViewEspacioDto> getListByArrayCategorias(ArrayCategoriasDto ac){
        List<Categoria> categorias = iCategoriaRepository.findAllById(ac.getCategorias());

        //Hacer un List<ViewEspacioDto> y setearle los espacios de las categorias con sus respectivos
        // promedios de valoraciones

        List<Espacio> espacios = new ArrayList<>();

        for (Categoria c:
             categorias) {
            //Hacer un metodo en este mismo Service que agregue los ViewEspacioDto y que ademas setee el promedio
            espacios.addAll(espacioRepository.findByCategoria(c));

        }

        List<ViewEspacioDto> viewEspaciosDto = espacioMapper.toViewDTOList(espacios);
        for (ViewEspacioDto item: viewEspaciosDto
             ) {
            item.setUsuarioPerfilDto(usuarioMapper.toDTO(usuario_espacio_permisoRepository.findUEPByIdEspacio(item.getIdEspacio()).getUsuario()));
            item.setPromedioValoraciones(valoracionService.getPromedioValoracionesByEspacio(item.getIdEspacio()));
            item.setCantidadMiembrosEspacio(usuario_espacio_permisoRepository.findAllByEspacioId(item.getIdEspacio()).size()-1);
        }

        return viewEspaciosDto;
    }


}
