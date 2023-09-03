package lapicito.backend.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lapicito.backend.dto.*;
import lapicito.backend.entity.*;
import lapicito.backend.enums.PermisoNombre;
import lapicito.backend.mapper.IUsuarioMapper;
import lapicito.backend.mapper.IUsuario_Espacio_PermisoMapper;
import lapicito.backend.repository.*;
import lapicito.backend.security.dto.JwtDto;
import lapicito.backend.security.enums.RolNombre;
import lapicito.backend.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolService rolService;

    @Autowired
    IUsuario_Espacio_PermisoRepository usuario_espacio_permisoRepository;

    @Autowired
    IEspacioRepository espacioRepository;

    @Autowired
    IPermisoRepository permisoRepository;

    @Autowired
    ICategoriaRepository categoriaRepository;

    @Autowired
    IEspacioService espacioService;

    @Autowired
    IPublicacionService publicacionService;

    @Autowired
    IValoracionService valoracionService;

    @Autowired
    IUsuario_Espacio_PermisoMapper usuarioEspacioPermisoMapper;

    @Autowired
    S3Service s3Service;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    IUsuarioMapper usuarioMapper;


    public Optional<Usuario> getByNombreUsuario(String userName) {
        return usuarioRepository.findByUserName(userName);
    }

    public Optional<Usuario> getByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> getById(int id){return usuarioRepository.findById(id); }

    public boolean existsByUserName(String userName) {
        return usuarioRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public boolean existById(int id){return usuarioRepository.existsById(id);}

    public Usuario saveCategoriasByUsuario(CategoriasDto categoriasDto) {
        Usuario usuario = usuarioRepository.findById(categoriasDto.getId_usuario()).get();
        usuario.setCategorias(categoriasDto.getCategorias());
        usuarioRepository.save(usuario);
        return usuario;
    }

    /*COMO MAPEAR UN DTO CON UNA ENTITY*/

    /*public Usuario saveEspaciosByUsuario(EspaciosDto espaciosDto) {
        Usuario usuario = usuarioRepository.findById(espaciosDto.getUsuario().getId()).get();
        Usuario_Espacio_Permiso uep = new Usuario_Espacio_Permiso(espaciosDto.getUsuario(), espaciosDto.getEspacio(), espaciosDto.getPermiso());
        usuario.setEspacios(espaciosDto.getUsuario().getEspacios());
        usuarioRepository.save(usuario);
        usuario_espacio_permisoRepository.save(uep);
        return usuario;
    }*/

    public void saveEspaciosByUsuario(EspacioDtoMultipart espaciosDto, MultipartFile portada_file) throws Exception {
        try{
            Usuario usuario = usuarioRepository.findById(espaciosDto.getId_usuario()).get();
            Permiso permiso = permisoRepository.findByPermisoNombre(PermisoNombre.ROLE_ADMIN);
            Categoria categoria = categoriaRepository.findById(espaciosDto.getId_categoria()).get();
            Espacio espacio =
                    new Espacio(espaciosDto.getTitulo(), espaciosDto.getDescripcion()
                            ,portada_file.getOriginalFilename(),categoria);
            InputStream inputStream =  new BufferedInputStream(portada_file.getInputStream());

            s3Service.upload(inputStream,portada_file.getOriginalFilename());
            espacioRepository.save(espacio);
            Usuario_Espacio_Permiso uep = new Usuario_Espacio_Permiso(usuario, espacio, permiso);
            usuario_espacio_permisoRepository.save(uep);

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public Set<Categoria> getCategoriasByIdUsuario(int id){
        Usuario usuario = usuarioRepository.findById(id).get();
        Set<Categoria> categorias = usuario.getCategorias();
        return categorias;
    }

    public List<Espacio> getEspaciosByIdUsuario(int id){
        //Usuario usuario = usuarioRepository.findById(id).get();
        //List<Usuario_Espacio_Permiso> uep = usuario_espacio_permisoRepository.findByIdUsuario(usuario.getId());
        List<Usuario_Espacio_Permiso> uep = usuario_espacio_permisoRepository.findAllByUsuarioId(id);
        List<Espacio> espacios = new ArrayList<Espacio>();
        for (Usuario_Espacio_Permiso item: uep){
            espacios.add(item.getEspacio());
        }
        return espacios;
    }

    public List<UEPDto> getUEPByIdUsuario(int id){
        List<UEPDto> uepDtoList = usuarioEspacioPermisoMapper.toDtoList(usuario_espacio_permisoRepository.findAllByUsuarioId(id));
        for (UEPDto item: uepDtoList
             ) {
            item.getViewEspacioDto().setPromedioValoraciones(valoracionService.getPromedioValoracionesByEspacio(item.getViewEspacioDto().getIdEspacio()));
            item.getViewEspacioDto().setUsuarioPerfilDto(usuarioMapper.toDTO(usuario_espacio_permisoRepository.findUEPByIdEspacio(item.getViewEspacioDto().getIdEspacio()).getUsuario()));
        }
        return uepDtoList;
    }

    public Usuario save(Usuario usuario) {
       usuarioRepository.save(usuario);
       return usuario;
   }

    public Usuario save(NuevoUsuarioDto nuevoUsuario) {
        try {
            Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(),
                        nuevoUsuario.getUserName(),passwordEncoder.encode(nuevoUsuario.getPassword()),
                    nuevoUsuario.getEmail());
            Set<Rol> roles = new HashSet<>();
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
            if (nuevoUsuario.getRoles().contains("admin")) {
                roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
            }
            Calendar fechaAlta = Calendar.getInstance();
            usuario.setFecha_alta(fechaAlta);
            usuario.setRoles(roles);
            usuario.setAvatar_url("avatar_generico");
            usuario.setCantidad_lapicitos(0);
            Usuario usuarioCreado = usuarioRepository.save(usuario);

            return  usuarioCreado;

        } catch (Exception e) {
            e.getMessage();
        }

        return null;
    }

        public boolean tieneCategorias(Usuario usuario) {
            return usuario.getCategorias().size() > 0;
        }

        public void updateAvatar(int id,String url){
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setAvatar_url(url);
        usuarioRepository.save(usuario);
    }
    public void updatePortada(int id,String url){
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setPortada_url(url);
        usuarioRepository.save(usuario);
    }

    public void updateDatosPersonales(int id, DatosPersonalesDto datosPersonalesDto) {
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setNombre(datosPersonalesDto.getNombre());
        usuario.setApellido(datosPersonalesDto.getApellido());
        usuario.setUserName(datosPersonalesDto.getUserName());
        usuario.setEmail(datosPersonalesDto.getEmail());
        usuario.setTelefono(datosPersonalesDto.getTelefono());
        usuarioRepository.save(usuario);
    }

    public String getAvatarPerfil(int id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        return usuario.getAvatar_url();
    }

    public String getPortadaPerfil(int id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        return usuario.getPortada_url();
    }

    public double getPromedioValoracionUsuario(int id){
        List<ViewPublicacionDto> viewPublicacionesDto = publicacionService.getViewListByIdUsuario(id);
        double sumaValoraciones = 0.0;
        int sumaPublicacionesValoradas = 0;

        for (ViewPublicacionDto item: viewPublicacionesDto) {
            if(valoracionService.getPromedioValoraciones(item.getIdPublicacion()) == 0.0){
                //sumaValoraciones += 0.0;
            }else{
                sumaValoraciones += valoracionService.getPromedioValoraciones(item.getIdPublicacion());
                sumaPublicacionesValoradas++;
            }

        }
        if(sumaPublicacionesValoradas == 0.0){
            return 0.0;
        }{
            return sumaValoraciones/sumaPublicacionesValoradas;
        }
    }

    public Usuario getByMpUserId(MPUser mpUser){

        return usuarioRepository.findByMpUser(mpUser);
    }

    public Usuario save(GoogleIdToken googleIdToken) {
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();

        String[] username = payload.getEmail().split("@");

        Usuario usuario = new Usuario(null,null
                ,username[0],passwordEncoder.encode(googleIdToken.getHeader().getKeyId())
                ,payload.getEmail());

        try{
            Set<Rol> roles = new HashSet<>();
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
            Calendar fechaAlta = Calendar.getInstance();
            usuario.setFecha_alta(fechaAlta);
            usuario.setRoles(roles);
            usuario.setAvatar_url("avatar_generico");
            usuario.setCantidad_lapicitos(0);


            return usuarioRepository.save(usuario);
        }catch (Exception e){
            System.out.println("Falla save usuario google");
            e.getMessage();
        }
        return null;
    }

    public JwtDto autenticacionJwt(String username, String password){
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username,password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario1 = getByNombreUsuario(username).get();
        JwtDto jwtDto = new JwtDto(usuario1.getId(),jwt,userDetails.getUsername(), userDetails.getAuthorities(),
                usuario1.getAvatar_url(),tieneCategorias(usuario1));
        return jwtDto;
    }

    public boolean UserNameDistintoDe(String userName, int id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        if(usuario.getUserName() == userName){
            return false;
        }
        return true;
    }

    public boolean EmailDistintoDe(String email, int id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        if(usuario.getEmail() == email){
            return false;
        }
        return true;
    }
}