package lapicito.backend.controller;

import com.mercadopago.exceptions.MPException;
import lapicito.backend.dto.MensajeDto;
import lapicito.backend.dto.MpTransactionDto;
import lapicito.backend.dto.MpUserDto;
import lapicito.backend.service.MercadoPagoService;
import lapicito.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;

@RestController
@RequestMapping (value = "/mercadoPago")
public class MercadoPagoController {


    @Autowired
    MercadoPagoService mercadoPagoService;

    @Autowired
    UsuarioService usuarioService;


    @PostMapping("/createAndRedirect")
    public ResponseEntity<?> createAndRedirect (@RequestBody MpTransactionDto mpTransactionDto, BindingResult bindingResult) throws MPException {

        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("campos mal puestos"), HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>(new MensajeDto(mercadoPagoService.createAndRedirect(mpTransactionDto)), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> vinculateAccessToken (@Valid @RequestBody MpUserDto mercadoPago, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("campos mal puestos"), HttpStatus.BAD_GATEWAY);
        }
        mercadoPagoService.saveMercadoPago(mercadoPago);

        return new ResponseEntity<>(new MensajeDto("Cuenta de MercadoPago vinculada"), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> desvinculateAccessToken(@PathVariable("id") int id){
         if(mercadoPagoService.existById(id)){

             mercadoPagoService.deleteById(id);

             return new ResponseEntity<>(new MensajeDto("Cuenta de MercadoPago desvinculada"), HttpStatus.OK);
         }

        return new ResponseEntity<>(new MensajeDto("La cuenta de MercadoPago no existe "), HttpStatus.BAD_REQUEST);
    }


}
