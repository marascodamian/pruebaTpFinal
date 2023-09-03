package lapicito.backend.controller;

import lapicito.backend.dto.CategoriasDto;
import lapicito.backend.dto.FullCategoriaDto;
import lapicito.backend.dto.MensajeDto;
import lapicito.backend.entity.Usuario;
import lapicito.backend.mapper.ICategoriaMapper;
import lapicito.backend.service.UsuarioService;
import lapicito.backend.service.CategoriaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categoria")
public class  CategoriaController {

    @Autowired
    CategoriaServiceImpl categoriaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ICategoriaMapper categoriaMapper;

    /*@GetMapping
    public List<FullCategoriaDto> listarCategorias(){

        List<FullCategoriaDto> fullCategoriaDtos = categoriaMapper.toDTOList(categoriaService.listarCategoria());
        return fullCategoriaDtos;
    }*/
    @GetMapping
    public ResponseEntity<?> listarCategorias(){

        return new ResponseEntity<>(categoriaMapper.toDTOList(categoriaService.listarCategoria()), HttpStatus.OK);

    }

    @PostMapping(value = "/usuario")
    public ResponseEntity<?> saveByUsuario(@Valid @RequestBody CategoriasDto categoriasDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("campos mal puestos"), HttpStatus.BAD_GATEWAY);
        }

        Usuario usuario;
        if(categoriasDto.getCategorias().size() >= 3) {
            usuario = usuarioService.saveCategoriasByUsuario(categoriasDto);
        }else{
            return new ResponseEntity(new MensajeDto("Debe elegir minimo 3 categorias"), HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity(new MensajeDto("Categorias guardadas en usuario: "+ usuario.getUserName()+", id: "+ usuario.getId()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/usuario/{id}")
    public ResponseEntity<?> getCategoriasByIdUsuario (@Valid @PathVariable int id){
        if(usuarioService.existById(id)){
            CategoriasDto categoriasDto =
                    new CategoriasDto(id,usuarioService.getCategoriasByIdUsuario(id));
            return new ResponseEntity(categoriasDto,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new MensajeDto("Usuario no encontrado"),
                    HttpStatus.BAD_REQUEST);
        }


    }

}