package lapicito.backend.controller;

import lapicito.backend.dto.MensajeDto;
import lapicito.backend.dto.ValoracionDto;
import lapicito.backend.entity.Usuario;
import lapicito.backend.entity.Valoracion;
import lapicito.backend.mapper.IValoracionMapper;
import lapicito.backend.service.IValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;

@RestController
@RequestMapping("/valoracion")
public class ValoracionController {

    @Autowired
    IValoracionService valoracionService;

    @Autowired
    IValoracionMapper valoracionMapper;


    @PostMapping
    public ResponseEntity<?> saveByUsuario(@Valid @RequestBody ValoracionDto valoracionDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("Valoracion Incompleta"), HttpStatus.BAD_GATEWAY);
        }
        if(!valoracionService.existsValoracionByUsuarioIdAndPublicacionId(valoracionDto.getId_usuario(),valoracionDto.getId_publicacion())){

            valoracionService.save(valoracionMapper.toEntity(valoracionDto));
            return new ResponseEntity(new MensajeDto("Valoracion guardada en publicacion: "+ valoracionDto.getId_publicacion()), HttpStatus.CREATED);
        }else{
            return new ResponseEntity(new MensajeDto("El usuario "+valoracionDto.getId_usuario()+" ya dejo su valoracion"), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(value = "/publicacion/{id}")
    public ResponseEntity<?> getValoracionesByPublicacion(@Valid @PathVariable int id){

        return new ResponseEntity(valoracionMapper.toDTOList(valoracionService.getValoracionByPublicacionId(id)), HttpStatus.OK);

    }
}
