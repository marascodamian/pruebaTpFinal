package lapicito.backend.controller;


import lapicito.backend.dto.*;
import lapicito.backend.entity.*;
import lapicito.backend.mapper.IPublicacionMapper;
import lapicito.backend.service.*;
import lapicito.backend.service.EspacioServiceImpl;
import lapicito.backend.service.IPublicacionServiceImpl;
import lapicito.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/publicacion")
public class PublicacionController {

    @Autowired
    IPublicacionServiceImpl publicacionService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EspacioServiceImpl espacioService;

    @Autowired
    CategoriaServiceImpl categoriaService;

    @Autowired
    IPublicacionMapper publicacionMapper;

    @Autowired
    IValoracionService valoracionService;

    @Autowired
    DescargaService descargaService;

    @Value("https://lapicito-bucket.s3.us-east-2.amazonaws.com/")
    private String aws_url_image;

    @PostMapping(value = "/usuario/{publicacion_file}")
    public ResponseEntity<?> savePublicacion(
            @Valid @RequestParam MultipartFile publicacion_file
            , @Valid @ModelAttribute PublicacionDto publicacionDto
            , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("campos mal puestos"), HttpStatus.BAD_GATEWAY);
        }
        if(!usuarioService.existById(publicacionDto.getId_usuario())){
            return new ResponseEntity((new MensajeDto("Usuario no existe")),HttpStatus.BAD_REQUEST);
        }
        if(!espacioService.existsById(publicacionDto.getId_espacio())){
            return new ResponseEntity((new MensajeDto("Publicacion debe contener un espacio")),HttpStatus.BAD_REQUEST);
        }
        publicacionService.save(publicacionDto,publicacion_file);
        return new ResponseEntity((new MensajeDto("Publicacion creada")),HttpStatus.OK);
    }

    @PutMapping(value = "/usuario/{publicacion_file}/{id}")
    public ResponseEntity<?> updatePublicacion(
            @Valid @RequestParam MultipartFile publicacion_file
            , @Valid @ModelAttribute PublicacionDto publicacionDto
            ,@Valid @RequestParam int id
            , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("campos mal puestos"), HttpStatus.BAD_GATEWAY);
        }
        if(!usuarioService.existById(publicacionDto.getId_usuario())){
            return new ResponseEntity((new MensajeDto("Usuario no existe")),HttpStatus.BAD_REQUEST);
        }
        if(!espacioService.existsById(publicacionDto.getId_espacio())){
            return new ResponseEntity((new MensajeDto("Publicacion debe contener un espacio")),HttpStatus.BAD_REQUEST);
        }
        publicacionService.updatePublicacion(publicacionDto,publicacion_file,id);
        //publicacionService.save(publicacionDto,publicacion_file);
        return new ResponseEntity((new MensajeDto("Publicacion modificada")),HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?>deletePublicacion(@Valid @RequestParam int id){
        if (!publicacionService.existById(id)){
            return new ResponseEntity((new MensajeDto("Publicación no existe")),HttpStatus.BAD_REQUEST);
        }
        publicacionService.deletePublicacion(id);
        return new ResponseEntity((new MensajeDto("Publicación eliminada")),HttpStatus.OK);
    }

    @GetMapping(value = "/usuario/{id}")
    public ResponseEntity<?> getListByIdUsuario(@Valid @PathVariable int id){
        if(!usuarioService.existById(id)){
            return new ResponseEntity((new MensajeDto("Usuario no existe")),HttpStatus.BAD_REQUEST);
        }

        List<ViewPublicacionDto> viewPublicacionesDto = publicacionService.getViewListByIdUsuario(id);
        return new ResponseEntity<>(viewPublicacionesDto,HttpStatus.OK);

    }

    @GetMapping(value = "/espacio/{id}")
    public ResponseEntity<?> getListByIdEspacio(@Valid @PathVariable int id){
       if(!espacioService.existsById(id)){
            return new ResponseEntity((new MensajeDto("Espacio no existe")),HttpStatus.BAD_REQUEST);
        }

        List<ViewPublicacionDto> viewPublicacionesDto = publicacionService.getViewListByIdEspacio(id);

        return new ResponseEntity<>(viewPublicacionesDto,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPublicacionById(@Valid @PathVariable int id){
        if(!publicacionService.existById(id)){
            return new ResponseEntity((new MensajeDto("Publicacion no existe")),HttpStatus.BAD_REQUEST);
        }

        ViewPublicacionConValoracionDto publicacion = publicacionService.getListByIdPublicacion(id);

        return new ResponseEntity<>(publicacion,HttpStatus.OK);
    }

    @PostMapping(value = "/categorias")
    public ResponseEntity<?> getListByArrayCategorias(@Valid @RequestBody ArrayCategoriasDto ac){

        for(Integer id: ac.getCategorias()){
            if(!categoriaService.existById(id)){
                return new ResponseEntity((new MensajeDto("Categoria no existe")),HttpStatus.BAD_REQUEST);
            };
        }
        List<ViewPublicacionDto> viewPublicacionesDto = publicacionService.getListByArrayCategorias(ac);
        if(viewPublicacionesDto.isEmpty()){
            return new ResponseEntity((new MensajeDto("La categoria o el espacio no contienen publicaciones")),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(viewPublicacionesDto,HttpStatus.OK);
    }

    @GetMapping(value = "/archivo-publicacion/{id}")
    public ResponseEntity<?> getUrlDescargaArchivoPublicacion(@Valid @PathVariable int id){
        if(!publicacionService.existById(id)){
            return new ResponseEntity(new MensajeDto("Publicacion no existe"),HttpStatus.BAD_REQUEST);
        }
        String archivoPublicacion = publicacionService.getArchivoById(id);
        return new ResponseEntity<>(new MensajeDto("La descarga se logueo con exito"),HttpStatus.OK);
    }

    @GetMapping(value = "/descargas/{id_publicacion}")
    public ResponseEntity<?> getCantidadDescargasByIdPublicacion(@Valid @PathVariable int id_publicacion){
        if(!publicacionService.existById(id_publicacion)){
            return new ResponseEntity((new MensajeDto("Publicacion no existe")),HttpStatus.BAD_REQUEST);
        }

        List<Descarga> descargas = descargaService.findByIdPublicacion(id_publicacion);

        return new ResponseEntity(descargas.size(),HttpStatus.OK);
    }

    @GetMapping(value = "/usuario/descargas/{id_usuario}")
    public ResponseEntity<?> getCantidadDescargasByIdUsuario(@Valid @PathVariable int id_usuario){
        if(!usuarioService.existById(id_usuario)){
            return new ResponseEntity((new MensajeDto("Usuario no existe")),HttpStatus.BAD_REQUEST);
        }

        List<Descarga> descargas = descargaService.findByIdUsuario(id_usuario);

        return new ResponseEntity(descargas.size(),HttpStatus.OK);
    }

    @PostMapping(value= "/search/{value}")
    public ResponseEntity<?> search(@Valid @PathVariable String value,
                                    @Valid @RequestBody ArrayCategoriasDto ac){
        List<ViewPublicacionDto> publicaciones = publicacionService.search(value,ac);
        return new ResponseEntity<>(publicaciones,HttpStatus.OK);
    }

}
