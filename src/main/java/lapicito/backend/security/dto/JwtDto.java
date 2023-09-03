package lapicito.backend.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtDto {
    private int id_usuario;
    private String token;
    private String bearer = "Bearer";
    private String userName;
    private Collection<? extends GrantedAuthority> authorities;
    private String avatar_url;
    private boolean tieneCategorias;

    public JwtDto(int id_usuario,String token, String userName, Collection<? extends GrantedAuthority> authorities,
                  String avatar_url, boolean tieneCategorias) {
        this.id_usuario = id_usuario;
        this.token = token;
        this.userName = userName;
        this.authorities = authorities;
        this.avatar_url = avatar_url;
        this.tieneCategorias = tieneCategorias;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public boolean isTieneCategorias() {
        return tieneCategorias;
    }

    public void setTieneCategorias(boolean tieneCategorias) {
        this.tieneCategorias = tieneCategorias;
    }
}
