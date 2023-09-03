package lapicito.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lapicito.backend.entity.Usuario;
import lapicito.backend.security.entity.UsuarioPrincipal;

import javax.transaction.Transactional;

@Service("userDetailServiceImpl")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByNombreUsuario(userName).get();
        return UsuarioPrincipal.build(usuario);
    }

   /* @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByEmail(email).orElseThrow(()->new UsernameNotFoundException("usuario no encontrado"));
        return UsuarioPrincipal.build(usuario);
    }*/
}
