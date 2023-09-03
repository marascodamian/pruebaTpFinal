package lapicito.backend.controller;

import lapicito.backend.dto.*;
import lapicito.backend.entity.Publicacion;
import lapicito.backend.entity.Usuario;
import lapicito.backend.mapper.IUsuarioMapper;
import lapicito.backend.service.IRankingService;
import lapicito.backend.service.RankingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventPublicationInterceptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/ranking")
public class RankingController {


    @Autowired
    IRankingService rankingService;

    @Autowired
    IUsuarioMapper usuarioMapper;

    @PostMapping(value = "/usuario")
    public ResponseEntity<?> getUsuariosMasDonados(@Valid @RequestBody ArrayCategoriasDto arrayCategoriasDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("campos mal puestos"), HttpStatus.BAD_GATEWAY);
        }

        List<UsuarioPerfilDto> listaUsuariosRankeados = usuarioMapper.toDtoList(rankingService.getUsuariosMasDonados(arrayCategoriasDto));

        return new ResponseEntity<>(listaUsuariosRankeados, HttpStatus.OK);
    }

    @PostMapping(value = "/publicacionValorada")
    public ResponseEntity<?> getPublicacionesMasValoradas(@Valid @RequestBody ArrayCategoriasDto arrayCategoriasDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("campos mal puestos"), HttpStatus.BAD_GATEWAY);
        }

        List<ViewPublicacionConValoracionDto> publicaciones = rankingService.getPublicacionesMasValoradas(arrayCategoriasDto);

        return new ResponseEntity<>(publicaciones, HttpStatus.OK);
    }

    @PostMapping(value = "/publicacionDescargada")
    public ResponseEntity<?> getPublicacionesMasDescargadas(@Valid @RequestBody ArrayCategoriasDto arrayCategoriasDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("campos mal puestos"), HttpStatus.BAD_GATEWAY);
        }

        List<ViewPublicacionConValoracionDto> publicaciones = rankingService.getPublicacionesMasDescargadas(arrayCategoriasDto);

        return new ResponseEntity<>(publicaciones, HttpStatus.OK);
    }

    @PostMapping(value = "/espacioSeguido")
    public ResponseEntity<?> getEspaciosMasSeguidos(@Valid @RequestBody ArrayCategoriasDto arrayCategoriasDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("campos mal puestos"), HttpStatus.BAD_GATEWAY);
        }

        List<ViewEspacioDto> espacioDtos = rankingService.getEspaciosMasSeguidos(arrayCategoriasDto);

        return new ResponseEntity<>(espacioDtos, HttpStatus.OK);
    }




}
