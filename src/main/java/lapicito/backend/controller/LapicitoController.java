package lapicito.backend.controller;

import lapicito.backend.dto.MensajeDto;
import lapicito.backend.dto.MpTransactionDto;
import lapicito.backend.service.ILapicitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping (value = "/lapicito")
public class LapicitoController {

    @Autowired
    ILapicitoService lapicitoService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MpTransactionDto mpTransactionDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("No se pudo registrar la donacion"), HttpStatus.BAD_GATEWAY);
        }

        lapicitoService.save(mpTransactionDto);
        return new ResponseEntity<>(new MensajeDto("Lapicito Donado correctamente"), HttpStatus.OK);
    }

    @GetMapping(value = "/usuario/{id}")
    public ResponseEntity<?> getViewLapicitoDtoByUsuario(@Valid @PathVariable int id){


            return new ResponseEntity<>(lapicitoService.getViewLapicitosDto(id), HttpStatus.OK);

    }
}
