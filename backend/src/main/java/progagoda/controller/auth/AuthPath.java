package progagoda.controller.auth;

import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/auth")
@ApplicationScoped
public class AuthPath {
    @EJB
    private AuthCore auth;

    public AuthPath() {
    }

    @POST
    @Path("/sign-in")
    public Response signIn(String json) throws NoSuchAlgorithmException, InvalidKeySpecException {
        ResponseBuilder rb = Response.ok();
        rb.header("Content-Type", "application/json;charset=UTF-8");
        AuthStatus st;
        if ((st = this.auth.init(json)) == AuthStatus.OK && (st = this.auth.signIn(json)) == AuthStatus.OK) {
            rb.status(200);
            Gson gson = new Gson();
            StringBuilder status = new StringBuilder();
            status.append("true");
            rb.entity(gson.toJson(status));
            return rb.build();
        } else {
            return this.auth.handleError(st, rb);
        }
    }

    @POST
    @Path("/sign-up")
    public Response signUp(String json) throws NoSuchAlgorithmException, InvalidKeySpecException {
        ResponseBuilder rb = Response.ok();
        rb.header("Content-Type", "application/json;charset=UTF-8");
        AuthStatus st;
        if ((st = this.auth.init(json)) == AuthStatus.OK && (st = this.auth.signUp(json)) == AuthStatus.OK) {
            rb.status(200);
            Gson gson = new Gson();
            StringBuilder status = new StringBuilder();
            status.append("true");
            rb.entity(gson.toJson(status));
            return rb.build();
        } else {
            return this.auth.handleError(st, rb);
        }
    }
}

