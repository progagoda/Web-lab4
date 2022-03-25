package progagoda.controller.auth;

import progagoda.model.entity.UserEntity;
import progagoda.model.proxy.UserProxy;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateful
public class AuthCore {
    private final Logger logger = LoggerFactory.getLogger(AuthCore.class);
    @EJB
    private UserProxy userProxy;
    private Long id;
    private String login;
    private char[] pass;

    public AuthStatus init(String json) {
        try {
            JSONObject object = new JSONObject(json);
            this.login = object.getString("login");
            this.pass = object.getString("password").toCharArray();
            return AuthStatus.OK;
        } catch (JSONException var3) {
            this.logger.error("JSON error while parsing \"{}\"", json, var3);
        } catch (Exception var4) {
            this.logger.error("Undefined error on request \"{}\"", json, var4);
        }

        return AuthStatus.REQUEST_ERROR;
    }

    private boolean isValid() {
        return this.login != null && this.pass != null;
    }

    private AuthStatus newUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (this.isValid()) {
            byte[] salt = PassHash.salt();
            byte[] hash = PassHash.hash(salt, this.pass);
            this.userProxy.save(new UserEntity(this.login, hash, salt));
            this.id = this.userProxy.findByLogin(this.login).getId();
            return AuthStatus.OK;
        } else {
            return AuthStatus.UNDEFINED_ERROR;
        }
    }

    public AuthStatus signIn() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (this.isValid()) {
            UserEntity user = this.userProxy.findByLogin(this.login);
            if (user == null) {
                return AuthStatus.NO_USER_FOUND;
            } else {
                this.id = user.getId();
                byte[] hash = PassHash.hash(user.getSalt(), this.pass);
                return !Arrays.equals(user.getPassword(), hash) ? AuthStatus.WRONG_PASSWORD : AuthStatus.OK;
            }
        } else {
            return AuthStatus.UNDEFINED_ERROR;
        }
    }

    public AuthStatus signUp() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (this.isValid()) {
            UserEntity user = this.userProxy.findByLogin(this.login);
            if (user != null) {
                return AuthStatus.USER_ALREADY_EXISTS;
            } else if (this.login.length() < 5) {
                return AuthStatus.TOO_SHORT_LOGIN;
            } else {
                return this.pass.length < 5 ? AuthStatus.TOO_SHORT_PASSWORD : this.newUser();
            }
        } else {
            return AuthStatus.UNDEFINED_ERROR;
        }
    }

    public Response handleError(AuthStatus st, ResponseBuilder rb) {
        rb.entity(String.format("{\"data\": \"%s\", \"status\": \"%b\"}", st.getDescription(), false));
        switch(st) {
            case USER_ALREADY_EXISTS:
            case TOO_SHORT_LOGIN:
            case TOO_SHORT_PASSWORD:
            case NO_USER_FOUND:
            case WRONG_PASSWORD:
                rb.status(401);
                break;
            case REQUEST_ERROR:
                rb.status(500);
                break;
            case UNDEFINED_ERROR:
                rb.status(520);
        }

        return rb.build();
    }

    public AuthCore() {
    }

    public Long getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public char[] getPass() {
        return this.pass;
    }
}

