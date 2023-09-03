package lapicito.backend.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lapicito.backend.dto.*;
import lapicito.backend.entity.*;
import lapicito.backend.mapper.*;
import lapicito.backend.security.dto.*;
import lapicito.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import lapicito.backend.security.jwt.JwtProvider;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class UsuarioController {


    @Value("${google.clientId}")
    private String googleClientId;

    @Value("${secretPsw}")
    String secretPsw;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;


    @Autowired
    IUsuario_Espacio_PermisoServiceImpl iusuario_espacio_permisoService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    IUsuarioMapper usuarioMapper;

    @Autowired
    S3Service s3Service;

    @Autowired
    IMailservice mailservice;

    @Value("https://lapicito-bucket.s3.us-east-2.amazonaws.com/")
    private String aws_url_image;

    final static String carpeta_avatar_perfil = "avatarPerfil";
    final static String carpeta_portada_perfil = "portadaPerfil";

    @PostMapping("/registrar")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuarioDto nuevoUsuario, BindingResult bindingResult) throws UnsupportedEncodingException, MessagingException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new MensajeDto("Peticion mal realizada"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsByUserName(nuevoUsuario.getUserName())) {
            return new ResponseEntity(new MensajeDto("UserName existente"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
            return new ResponseEntity(new MensajeDto("Email existente"), HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = usuarioService.save(nuevoUsuario);
        if(usuario != null){
            mailservice.enviarMailBienvenida(nuevoUsuario.getEmail(),nuevoUsuario.getUserName());
            return new ResponseEntity(new MensajeDto("Usuario guardado"), HttpStatus.CREATED);
        }else {
            return new ResponseEntity(new MensajeDto("Error al crear usuario"), HttpStatus.BAD_GATEWAY);
        }

    }
    //ver logout
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login (@Valid @RequestBody LoginUsuarioDto loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new MensajeDto("campos mal puestos"), HttpStatus.BAD_GATEWAY);
        }
        if(usuarioService.existsByUserName(loginUsuario.getUserName())){
            JwtDto jwtDto =
                    usuarioService.autenticacionJwt(loginUsuario.getUserName(),loginUsuario.getPassword());
            return new ResponseEntity(jwtDto, HttpStatus.OK);}
        else{
            return new ResponseEntity(new MensajeDto("Usuario no encontrado"),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/perfil/{id}")
    public ResponseEntity<?> perfil (@Valid @PathVariable int id){
        if(usuarioService.existById(id)) {
            UsuarioPerfilDto viewUsuario = usuarioMapper.toDTO(usuarioService.getById(id).get());
            viewUsuario.setPromedioUsuario(usuarioService.getPromedioValoracionUsuario(id));
            return new ResponseEntity<>(viewUsuario, HttpStatus.OK);
        }else{
            return new ResponseEntity(new MensajeDto("Usuario no encontrado"),HttpStatus.BAD_REQUEST);
        }
    };

    @PostMapping("/avatarUrl/{id}")
    public ResponseEntity<?> updateAvatarUrl(@Valid @PathVariable int id,
                                             @Valid @RequestParam MultipartFile avatarImg) throws IOException {
        if(!usuarioService.existById(id)){
            return new ResponseEntity<MensajeDto>(new MensajeDto("Usuario no encontrado"),HttpStatus.BAD_REQUEST);
        }
        InputStream inputStream =  new BufferedInputStream(avatarImg.getInputStream());

        s3Service.upload(inputStream,avatarImg.getOriginalFilename());
        usuarioService.updateAvatar(id,avatarImg.getOriginalFilename());
        return new ResponseEntity<MensajeDto>(new MensajeDto("Avatar modificado"),HttpStatus.OK);
    }

    @PostMapping("/portadaUrl/{id}")
    public ResponseEntity<?> updatePortadaUrl(@Valid @PathVariable int id,
                                              @Valid @RequestParam MultipartFile portadaImg) throws IOException {
        if(!usuarioService.existById(id)){
            return new ResponseEntity<MensajeDto>(new MensajeDto("Usuario no encontrado"),HttpStatus.BAD_REQUEST);
        }
        InputStream inputStream =  new BufferedInputStream(portadaImg.getInputStream());
        s3Service.upload(inputStream,portadaImg.getOriginalFilename());
        usuarioService.updatePortada(id,portadaImg.getOriginalFilename());
        return new ResponseEntity<MensajeDto>(new MensajeDto("Portada modificada"),HttpStatus.OK);
    }

    @PostMapping("/perfil/{id}")
    public ResponseEntity<?> updateDatosPersonales(@Valid @PathVariable int id,
                                               @Valid @RequestBody DatosPersonalesDto datosPersonalesDto){
        if(!usuarioService.existById(id)){
            return new ResponseEntity<MensajeDto>(new MensajeDto("Usuario no encontrado"),HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsByUserName(datosPersonalesDto.getUserName())
                && !usuarioService.UserNameDistintoDe(datosPersonalesDto.getUserName(),id)) {
            return new ResponseEntity(new MensajeDto("UserName existente"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsByEmail(datosPersonalesDto.getEmail())
                && !usuarioService.EmailDistintoDe(datosPersonalesDto.getEmail(),id)) {
            return new ResponseEntity(new MensajeDto("Email existente"), HttpStatus.BAD_REQUEST);
        }
        usuarioService.updateDatosPersonales(id,datosPersonalesDto);
        return new ResponseEntity<MensajeDto>(new MensajeDto("Datos personales modificados"),HttpStatus.OK);
    }

    @GetMapping("/avatar/perfil/{id}")
    public ResponseEntity<?> getAvatarPerfil(@Valid @PathVariable int id) throws IOException {
        if(!usuarioService.existById(id)){
            return new ResponseEntity<MensajeDto>(new MensajeDto("Usuario no encontrado"),HttpStatus.BAD_REQUEST);
        }
        String avatarPerfil = usuarioService.getAvatarPerfil(id);
        return new ResponseEntity<>(avatarPerfil, HttpStatus.OK);
    }

    @GetMapping("/portada/perfil/{id}")
    public ResponseEntity<?> getPortadaPerfil(@Valid @PathVariable int id){
        if(!usuarioService.existById(id)){
            return new ResponseEntity<MensajeDto>(new MensajeDto("Usuario no encontrado"),HttpStatus.BAD_REQUEST);
        }
        String portadaPerfil = usuarioService.getPortadaPerfil(id);
        return new ResponseEntity<>(portadaPerfil, HttpStatus.OK);
    }


    //ver combinación con nuestro jwt e impactar el usuario en la base
    @PostMapping("/google")
    public ResponseEntity<TokenSocialDto> google (@RequestBody TokenSocialDto tokenSocialDto) throws IOException {

        final NetHttpTransport netHttpTransport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier.Builder verifier =
                new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory)
                        .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), tokenSocialDto.getValue());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();

        if(usuarioService.existsByEmail(payload.getEmail())) {
            Usuario usuario = usuarioService.getByEmail(payload.getEmail()).get();
            JwtDto jwtDto = usuarioService
                    .autenticacionJwt(usuario.getUserName(),googleIdToken.getHeader().getKeyId());
                return new ResponseEntity(jwtDto, HttpStatus.OK);
            } else{
            //registra el usuario
            Usuario usuario = usuarioService.save(googleIdToken);
            //devuelve jwt nuestro
            JwtDto jwtDto = usuarioService
                    .autenticacionJwt(usuario.getUserName(),googleIdToken.getHeader().getKeyId());
            return new ResponseEntity(jwtDto, HttpStatus.OK);
        }
    }

    //ver combinación con nuestro jwt e impactar el usuario en la base
    @PostMapping("/facebook")
    public ResponseEntity<TokenSocialDto> facebook (@RequestBody TokenSocialDto tokenSocialDto) throws IOException {

        Facebook facebook = new FacebookTemplate(tokenSocialDto.getValue());
        final String [] fields = {"email", "picture"};
        // que datos mostrar con el fields o los datos que necesite
        User user = facebook.fetchObject("me", User.class, fields);

        /*Usuario usuario = new Usuario();
        if(usuarioService.existsByEmail(user.getEmail()))
            usuario = usuarioService.getByEmail(user.getEmail()).get();
        else
            usuario = saveUsuario(user.getEmail());
        TokenSocialDto tokenRes = login(usuario);*/

        return new ResponseEntity(user, HttpStatus.OK);
    }


    private String guardarImagen(MultipartFile avatarImg, String carpeta) {
        if(!avatarImg.isEmpty()){
            switch (carpeta){
                case carpeta_avatar_perfil:
                    Path directorioImagenes = Paths.get(
                            "src//main//resources//static//images//avatarPerfil");
                    String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
                    try{
                        byte[] bytesImg = avatarImg.getBytes();
                        Path rutaCompleta = Paths.get
                                (rutaAbsoluta + "//" + avatarImg.getOriginalFilename());
                        Files.write(rutaCompleta,bytesImg);
                        return "avatarPerfil/" + avatarImg.getOriginalFilename();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case carpeta_portada_perfil:
                    Path directorioImagenes1 = Paths.get(
                            "src//main//resources//static//images//portadaPerfil");
                    String rutaAbsoluta1 = directorioImagenes1.toFile().getAbsolutePath();
                    try{
                        byte[] bytesImg = avatarImg.getBytes();
                        Path rutaCompleta = Paths.get
                                (rutaAbsoluta1 + "//" + avatarImg.getOriginalFilename());
                        Files.write(rutaCompleta,bytesImg);
                        return "portadaPerfil/" + avatarImg.getOriginalFilename();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
        return null;
    }

}
