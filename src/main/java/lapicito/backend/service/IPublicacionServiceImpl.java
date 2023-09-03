package lapicito.backend.service;

import lapicito.backend.dto.*;
import lapicito.backend.entity.*;
import lapicito.backend.mapper.IEspacioMapper;
import lapicito.backend.mapper.IPublicacionMapper;
import lapicito.backend.mapper.IValoracionMapper;
import lapicito.backend.repository.ICategoriaRepository;
import lapicito.backend.repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class IPublicacionServiceImpl implements IPublicacionService {

    @Autowired
    PublicacionRepository publicacionRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EspacioServiceImpl espacioService;

    @Autowired
    IPublicacionMapper publicacionMapper;

    @Autowired
    IValoracionService valoracionService;

    @Autowired
    IEspacioMapper espacioMapper;

    @Autowired
    IValoracionMapper valoracionMapper;

    @Autowired
    S3Service s3Service;

    @Autowired
    DescargaService descargaService;

    @Autowired
    ICategoriaRepository iCategoriaRepository;

    public void save(PublicacionDto publicacionDto, MultipartFile publicacion_file){

        Calendar fechaAlta = Calendar.getInstance();
        Usuario usuario = usuarioService.getById(publicacionDto.getId_usuario()).get();
        Espacio espacio = espacioService.getById(publicacionDto.getId_espacio()).get();

        Publicacion publicacion =
                new Publicacion(publicacionDto.getTitulo(), publicacionDto.getDescripcion(),
                publicacion_file.getOriginalFilename(),publicacionDto.getEs_anuncio(),publicacionDto.getActivo(),
                fechaAlta,usuario,espacio);

        try {
            InputStream inputStream =  new BufferedInputStream(publicacion_file.getInputStream());

            s3Service.upload(inputStream,publicacion_file.getOriginalFilename());
            publicacionRepository.save(publicacion);
        }catch(Exception e){
            e.getMessage();
        }

    }

    public void updatePublicacion(PublicacionDto publicacionDto, MultipartFile publicacion_file, int id){

        Publicacion publicacion = publicacionRepository.findById(id).get();

        publicacion.setTitulo(publicacionDto.getTitulo());
        publicacion.setDescripcion(publicacionDto.getDescripcion());
        publicacion.setUrl_adjunto(publicacion_file.getOriginalFilename());
        publicacion.setEs_anuncio(publicacionDto.getEs_anuncio());
        publicacion.setActivo(publicacionDto.getActivo());

        try {
            InputStream inputStream =  new BufferedInputStream(publicacion_file.getInputStream());

            s3Service.upload(inputStream,publicacion_file.getOriginalFilename());
            publicacionRepository.save(publicacion);
        }catch(Exception e){
            e.getMessage();
        }


    }

    public void deletePublicacion(int id){
        publicacionRepository.deleteById(id);
    }


    public List<ViewPublicacionDto> getViewListByIdUsuario(int id){
        Usuario usuario = usuarioService.getById(id).get();
        List<ViewPublicacionDto> viewPublicacionesDto = publicacionMapper.toDTOList(publicacionRepository.findByUsuario(usuario));

        for (ViewPublicacionDto item: viewPublicacionesDto
             ) {
                item.setPromedioValoracion(valoracionService.getPromedioValoraciones(item.getIdPublicacion()));
        }
        return viewPublicacionesDto;
    }

    public List<ViewPublicacionDto> getViewListByIdEspacio(int id) {
        Espacio espacio = espacioService.getById(id).get();
        List<ViewPublicacionDto> viewPublicacionesDto = publicacionMapper.toDTOList(publicacionRepository.findByEspacio(espacio));
        for (ViewPublicacionDto item: viewPublicacionesDto
        ) {
            item.setPromedioValoracion(valoracionService.getPromedioValoraciones(item.getIdPublicacion()));
        }
        return viewPublicacionesDto;
    }

    public boolean existById(int id) {
        return publicacionRepository.existsById(id);
    }

    public ViewPublicacionConValoracionDto getListByIdPublicacion(int id) {


        ViewPublicacionConValoracionDto publicacionDto = publicacionMapper.toConValoracionDto(publicacionRepository.findById(id).get());
        publicacionDto.setPromedioValoracion(valoracionService.getPromedioValoraciones(publicacionDto.getIdPublicacion()));
        publicacionDto.setValoracionDtoList(valoracionMapper.toDTOList(valoracionService.getValoracionByPublicacionId(id)));
        publicacionDto.setCantidadDeDescargas(descargaService.findByIdPublicacion(id).size());

        return publicacionDto;

    }

    public List<ViewPublicacionDto> getListByArrayCategorias(ArrayCategoriasDto ac) {
        //obtener espacios by id categorias.
        List<ViewEspacioDto> espacios = espacioService.getListByArrayCategorias(ac);

        int[] espaciosId = new int[espacios.size()];

        //obtener publicaciones by id espacios
        List<ViewPublicacionDto> viewPublicacionesDto = new ArrayList<>();


        for (int i = 0; i < espaciosId.length; i++){
            espaciosId[i] = espacios.get(i).getIdEspacio();
        }

        viewPublicacionesDto.addAll(publicacionMapper.toDTOList(publicacionRepository.findByEspacioOrdenado(espaciosId)));

        for (ViewPublicacionDto item: viewPublicacionesDto
        ) {
            item.setPromedioValoracion(valoracionService.getPromedioValoraciones(item.getIdPublicacion()));
        }


        return viewPublicacionesDto;
    }

    public String getArchivoById(int id) {
        try{
            Publicacion publicacion = publicacionRepository.findById(id).get();
            Descarga descarga = new Descarga(publicacion.getUsuario(),publicacion);
            descargaService.save(descarga);
            return publicacion.getUrl_adjunto();
        }catch (Exception e){
            e.getMessage();
        }

        return null;
    }

    public Publicacion findById(int id_publicacion){
        return publicacionRepository.findById(id_publicacion).get();
    }


    public List<ViewPublicacionDto> search(String value,ArrayCategoriasDto ac) {
        List<ViewPublicacionDto> publicaciones = new ArrayList<>();
        List<ViewEspacioDto> espacios = espacioService.getListByArrayCategorias(ac);

        for (ViewEspacioDto e:
                espacios) {
            publicaciones.addAll(publicacionMapper.toDTOList(publicacionRepository.search(value,espacioMapper.toViewEntity(e).getId())));
        }

        return publicaciones;
    }
}
