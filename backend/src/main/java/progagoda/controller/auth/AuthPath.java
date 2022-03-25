package progagoda.controller.auth;

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
        if ((st = this.auth.init(json)) == AuthStatus.OK && (st = this.auth.signIn()) == AuthStatus.OK) {
            rb.status(200);
            rb.entity(String.format("{\"status\": \"%b\"}", true));
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
        if ((st = this.auth.init(json)) == AuthStatus.OK && (st = this.auth.signUp()) == AuthStatus.OK) {
            rb.status(200);
            rb.entity(String.format("{\"status\": \"%b\"}", true));
            return rb.build();
        } else {
            return this.auth.handleError(st, rb);
        }
    }
}

