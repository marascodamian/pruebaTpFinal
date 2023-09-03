package lapicito.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lapicito.backend.dto.MensajeDto;

@RestController
@RequestMapping(value = "/")
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<?> getHome(){
        return new ResponseEntity(new MensajeDto("Hola"), HttpStatus.OK);
    }

    @GetMapping("/pruebaJwt")
    public ResponseEntity<?> getPruebaJwt(){
        return new ResponseEntity(new MensajeDto("Prueba jwt exitosa"), HttpStatus.OK);
    }
}
