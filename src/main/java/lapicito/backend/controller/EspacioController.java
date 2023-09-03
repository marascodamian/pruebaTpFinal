package lapicito.backend.controller;

import lapicito.backend.dto.*;
import lapicito.backend.entity.Categoria;
import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Publicacion;
import lapicito.backend.entity.Usuario;
import lapicito.backend.mapper.IEspacioMapper;
import lapicito.backend.mapper.IUsuario_Espacio_PermisoMapper;
import lapicito.backend.service.ICategoriaService;
import lapicito.backend.service.IEspacioService;
import lapicito.backend.service.IUsuario_Espacio_PermisoService;
import lapicito.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/espacio")
public class EspacioController {

    @Autowired
    IEspacioService espacioService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ICategoriaService categoriaService;

    @Autowired
    IEspacioMapper espacioMapper;

    @Autowired
    IUsuario_Espacio_PermisoMapper usuario_espacio_permisoMapper;

    @Autowired
    IUsuario_Espacio_PermisoService usuario_espacio_permisoService;


    @PostMapping(value = "/usuario/{portada_file}")
    public ResponseEntity<?> saveByUsuario(@Valid @RequestParam MultipartFile portada_file
            , @Valid @ModelAttribute EspacioDtoMultipart espaciosDto, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("campos mal puestos"), HttpStatus.BAD_GATEWAY);
        }

        Usuario usuario = usuarioService.getById(espaciosDto.getId_usuario()).get();
        usuarioService.saveEspaciosByUsuario(espaciosDto,portada_file);

        return new ResponseEntity(new MensajeDto("Espacio guardado en usuario: "+ usuario.getUserName()+", id: "+ usuario.getId()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getEspacioById(@Valid @PathVariable int id){
        if(espacioService.existsById(id)){
            return new ResponseEntity<>(espacioService.getDtoById(id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new MensajeDto("Espacio no encontrado"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/usuario/{id}")
    public ResponseEntity<?> getEspaciosByUsuario(@Valid @PathVariable int id){
        if(usuarioService.existById(id)){
            List<UEPDto> uepDto = usuarioService.getUEPByIdUsuario(id);

            return new ResponseEntity<>(uepDto,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new MensajeDto("Usuario no encontrado"),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/categorias")
    public ResponseEntity<?> getEspaciosByCategorias(@Valid @RequestBody ArrayCategoriasDto ac){

        for(Integer id: ac.getCategorias()){
            if(!categoriaService.existById(id)){
                return new ResponseEntity((new MensajeDto("Categoria no existe")),HttpStatus.BAD_REQUEST);
            };
        }
        List<ViewEspacioDto> viewEspaciosDto = espacioService.getListByArrayCategorias(ac);
        if(viewEspaciosDto.isEmpty()){
            return new ResponseEntity((new MensajeDto("La categoria no contiene Espacios")),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(viewEspaciosDto,HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<?> updateEspacioByUsuario(@Valid @RequestBody Espacio espacio, BindingResult bindingResult){

        if(espacioService.existsById(espacio.getId())){
            espacioService.update(espacio);
            return new ResponseEntity<>(new MensajeDto("El Espacio ha sido modificado"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new MensajeDto("Espacio no encontrado"),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping
    public ResponseEntity<?> deleteEspacioByUsuario(@Valid @RequestBody int id, BindingResult bindingResult){

        if(espacioService.existsById(id)){
            espacioService.delete(id);

            return new ResponseEntity<>(new MensajeDto("El Espacio ha sido eliminado"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new MensajeDto("Espacio no encontrado"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/unirme/{espacio_id}/{usuario_id}")
    public ResponseEntity<?> unirmeAEspacio(@Valid @PathVariable int espacio_id,
                                            @Valid @PathVariable int usuario_id){

        if(!espacioService.existsById(espacio_id)){
            return new ResponseEntity<>(new MensajeDto("Espacio no encontrado"),
                    HttpStatus.BAD_REQUEST);
        }else{
            usuario_espacio_permisoService.saveSeguidor(espacio_id,usuario_id);
            return new ResponseEntity<>(new MensajeDto("Espacio Seguido"),HttpStatus.OK);
        }
    }

    @PostMapping(value = "/dejarDeSeguir/{espacio_id}/{usuario_id}")
    public ResponseEntity<?> dejarDeSeguirAEspacio(@Valid @PathVariable int espacio_id,
                                            @Valid @PathVariable int usuario_id){

        if(!espacioService.existsById(espacio_id)){
            return new ResponseEntity<>(new MensajeDto("Espacio no encontrado"),
                    HttpStatus.BAD_REQUEST);
        }else{
            usuario_espacio_permisoService.deleteSeguidor(espacio_id,usuario_id);
            return new ResponseEntity<>(new MensajeDto("espacio dejar de seguir"),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/seguidores/{id}")
    public ResponseEntity<?> getCantidadSeguidores(@Valid @PathVariable int id){
        if(!espacioService.existsById(id)){
            return new ResponseEntity<>(new MensajeDto("Espacio no encontrado"),
                    HttpStatus.BAD_REQUEST);
        }else{
            int cantidadSeguidores = usuario_espacio_permisoService.getCantidadSeguidores(id);
            return new ResponseEntity<>(cantidadSeguidores,HttpStatus.OK);
        }


    }

    @GetMapping(value = "/seguidores/{id_espacio}/{id_usuario}")
    public ResponseEntity<?> getUsuarioSigueEspacio(@Valid @PathVariable int id_espacio,
                                                    @Valid @PathVariable int id_usuario){

        if(!espacioService.existsById(id_espacio)){
            return new ResponseEntity<>(new MensajeDto("Espacio no encontrado"),
                    HttpStatus.BAD_REQUEST);
        }else{
            boolean sigueEspacio = usuario_espacio_permisoService.getSigueEspacio(id_espacio,id_usuario);
            return new ResponseEntity<>(sigueEspacio,HttpStatus.OK);
        }
    }
    @PostMapping(value= "/search/{value}")
    public ResponseEntity<?> search(@Valid @PathVariable String value,
                                    @Valid @RequestBody ArrayCategoriasDto ac){

        List<ViewEspacioDto> espacios = espacioService.search(value,ac);


        return new ResponseEntity<>(espacios,HttpStatus.OK);
    }

}
