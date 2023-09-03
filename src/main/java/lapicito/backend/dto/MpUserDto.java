package lapicito.backend.dto;

import lapicito.backend.entity.Usuario;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

public class MpUserDto {

    private long user_id;
    private String access_token;
    private boolean live_mode;
    private String public_key;
    private String refresh_token;
    private String scope;
    private String token_type;
    private int idUsuario;

    public MpUserDto() {
    }

    public MpUserDto(long user_id, String access_token, boolean live_mode, String public_key, String refresh_token, String scope, String token_type, int idUsuario) {

        this.user_id = user_id;
        this.access_token = access_token;
        this.live_mode = live_mode;
        this.public_key = public_key;
        this.refresh_token = refresh_token;
        this.scope = scope;
        this.token_type = token_type;
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }


    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public boolean isLive_mode() {
        return live_mode;
    }

    public void setLive_mode(boolean live_mode) {
        this.live_mode = live_mode;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }


}
