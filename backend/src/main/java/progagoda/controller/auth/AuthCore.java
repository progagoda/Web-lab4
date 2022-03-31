package progagoda.controller.auth;

import com.google.gson.Gson;
import progagoda.model.entity.UserEntity;
import progagoda.model.proxy.UserProxy;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class AuthCore {
    private final Logger logger = LoggerFactory.getLogger(AuthCore.class);
    @EJB
    private UserProxy userProxy;
    private Long id;
    private String login;
    private char[] pass;

    public AuthStatus init(String json) {
        try {
            Gson object = new Gson();
            AuthCore authCore = object.fromJson(json,AuthCore.class);
            return AuthStatus.OK;
        } catch (JSONException var3) {
            this.logger.error("JSON error while parsing \"{}\"", json, var3);
        } catch (Exception var4) {
            this.logger.error("Undefined error on request \"{}\"", json, var4);
        }

        return AuthStatus.REQUEST_ERROR;
    }

    private boolean isValid(String json) {
        Gson object = new Gson();
        AuthCore authCore = object.fromJson(json,AuthCore.class);
        char[] pass= authCore.pass;
        String login = authCore.login;
        return login != null && pass != null;
    }

    private AuthStatus newUser(String json) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (this.isValid(json)) {
            Gson object = new Gson();
            AuthCore authCore = object.fromJson(json,AuthCore.class);
            Long id = authCore.id;
            char[] pass= authCore.pass;
            String login = authCore.login;
            UserProxy userProxy = authCore.userProxy;
            byte[] salt = PassHash.salt();
            byte[] hash = PassHash.hash(salt, pass);
            this.userProxy.save(new UserEntity(login, hash, salt));
            this.id = userProxy.findByLogin(login).getId();
            return AuthStatus.OK;
        } else {
            return AuthStatus.UNDEFINED_ERROR;
        }
    }

    public AuthStatus signIn(String json) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (this.isValid(json)) {
            Gson object = new Gson();
            AuthCore authCore = object.fromJson(json,AuthCore.class);
            Long id = authCore.id;
            char[] pass= authCore.pass;
            String login = authCore.login;
            UserProxy userProxy = authCore.userProxy;
            UserEntity user = userProxy.findByLogin(login);
            if (user == null) {
                return AuthStatus.NO_USER_FOUND;
            } else {
                id = user.getId();
                byte[] hash = PassHash.hash(user.getSalt(), pass);
                return !Arrays.equals(user.getPassword(), hash) ? AuthStatus.WRONG_PASSWORD : AuthStatus.OK;
            }
        } else {
            return AuthStatus.UNDEFINED_ERROR;
        }
    }

    public AuthStatus signUp(String json) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (this.isValid(json)) {
            Gson object = new Gson();
            AuthCore authCore = object.fromJson(json,AuthCore.class);
            Long id = authCore.id;
            char[] pass= authCore.pass;
            String login = authCore.login;
            UserProxy userProxy = authCore.userProxy;
            UserEntity user = userProxy.findByLogin(login);
            if (user != null) {
                return AuthStatus.USER_ALREADY_EXISTS;
            } else if (login.length() < 5) {
                return AuthStatus.TOO_SHORT_LOGIN;
            } else {
                return this.pass.length < 5 ? AuthStatus.TOO_SHORT_PASSWORD : newUser(json);
            }
        } else {
            return AuthStatus.UNDEFINED_ERROR;
        }
    }

    public Response handleError(AuthStatus st, ResponseBuilder rb) {
        Gson gson = new Gson();
        StringBuilder status = new StringBuilder();
        status.append("true");
        rb.entity(gson.toJson(status));
        rb.entity(gson.toJson(st.getDescription()));
        rb.entity(gson.toJson(status));
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

