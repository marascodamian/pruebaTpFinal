package lapicito.backend.service;

import lapicito.backend.dto.*;
import lapicito.backend.entity.*;
import lapicito.backend.mapper.IUsuarioMapper;
import lapicito.backend.repository.ICategoriaRepository;
import lapicito.backend.repository.IUsuario_Espacio_PermisoRepository;
import lapicito.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RankingServiceImpl implements  IRankingService{


    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ICategoriaRepository categoriaRepository;

    @Autowired
    IUsuarioMapper usuarioMapper;

    @Autowired
    IEspacioService espacioService;

    @Autowired
    IUsuario_Espacio_PermisoRepository uepRepository;

    @Autowired
    IPublicacionService publicacionService;

    @Override
    public List<Usuario> getUsuariosMasDonados(ArrayCategoriasDto ac) {

        List<ViewEspacioDto> espacios = espacioService.getListByArrayCategorias(ac);

        List<Usuario> usuarios = new ArrayList<>();

        for (ViewEspacioDto item : espacios
             ) {
            Usuario_Espacio_Permiso uep = uepRepository.findUEPByIdEspacio(item.getIdEspacio());
            if (!usuarios.contains(uep.getUsuario())) {
                usuarios.add(uep.getUsuario());
            }
        }

        Collections.sort(usuarios, new Comparator<Usuario>() {
            @Override
            public int compare(Usuario u1, Usuario u2) {
                return new Integer(u2.getCantidad_lapicitos()).compareTo(new Integer(u1.getCantidad_lapicitos()));
            }
        });

        usuarios = usuarios.stream().limit(3).collect(Collectors.toList());

        return usuarios;
    }

    @Override
    public List<ViewPublicacionConValoracionDto> getPublicacionesMasValoradas(ArrayCategoriasDto ac) {

        List<ViewPublicacionDto> publicaciones = publicacionService.getListByArrayCategorias(ac);

        List<ViewPublicacionConValoracionDto> publicacionConValoracionDtos = new ArrayList<>();

        for (ViewPublicacionDto item: publicaciones
             ) {
                publicacionConValoracionDtos.add(publicacionService.getListByIdPublicacion(item.getIdPublicacion()));
        }

        Collections.sort(publicacionConValoracionDtos, new Comparator<ViewPublicacionConValoracionDto>() {
            @Override
            public int compare(ViewPublicacionConValoracionDto v1, ViewPublicacionConValoracionDto v2) {
                return new Double(v2.getValoracionDtoList().size()).compareTo(new Double(v1.getValoracionDtoList().size()));
            }
        });

        publicacionConValoracionDtos = publicacionConValoracionDtos.stream().limit(3).collect(Collectors.toList());

        return publicacionConValoracionDtos;
    }

    @Override
    public List<ViewPublicacionConValoracionDto> getPublicacionesMasDescargadas(ArrayCategoriasDto ac) {

        List<ViewPublicacionDto> publicaciones = publicacionService.getListByArrayCategorias(ac);

        List<ViewPublicacionConValoracionDto> publicacionConValoracionDtos = new ArrayList<>();

        for (ViewPublicacionDto item: publicaciones
        ) {
            publicacionConValoracionDtos.add(publicacionService.getListByIdPublicacion(item.getIdPublicacion()));
        }

        Collections.sort(publicacionConValoracionDtos, new Comparator<ViewPublicacionConValoracionDto>() {
            @Override
            public int compare(ViewPublicacionConValoracionDto v1, ViewPublicacionConValoracionDto v2) {
                return new Integer(v2.getCantidadDeDescargas()).compareTo(new Integer(v1.getCantidadDeDescargas()));
            }
        });

        publicacionConValoracionDtos = publicacionConValoracionDtos.stream().limit(3).collect(Collectors.toList());

        return publicacionConValoracionDtos;
    }

    @Override
    public List<ViewEspacioDto> getEspaciosMasSeguidos(ArrayCategoriasDto ac) {

        List<ViewEspacioDto> espacios = espacioService.getListByArrayCategorias(ac);


        for (ViewEspacioDto item : espacios
        ) {
            item.setUsuarioPerfilDto(usuarioMapper.toDTO(uepRepository.findUEPByIdEspacio(item.getIdEspacio()).getUsuario()));
            item.setCantidadMiembrosEspacio(uepRepository.findAllByEspacioId(item.getIdEspacio()).size()-1);
        }

        Collections.sort(espacios, new Comparator<ViewEspacioDto>() {
            @Override
            public int compare(ViewEspacioDto v1, ViewEspacioDto v2) {
                return new Integer(v2.getCantidadMiembrosEspacio()).compareTo(new Integer(v1.getCantidadMiembrosEspacio()));
            }
        });

        espacios = espacios.stream().limit(3).collect(Collectors.toList());

        return espacios;
    }


}
