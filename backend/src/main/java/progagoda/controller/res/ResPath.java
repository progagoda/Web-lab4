package progagoda.controller.res;


import progagoda.controller.auth.AuthCore;
import progagoda.controller.auth.AuthStatus;
import progagoda.model.entity.ResponseEntity;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/result")
@ApplicationScoped
public class ResPath {
    @EJB
    private AuthCore auth;
    @EJB
    private ResCore res;

    public ResPath() {
    }

    @POST
    public Response result(String json) throws NoSuchAlgorithmException, InvalidKeySpecException {
        ResponseBuilder rb = Response.ok();
        rb.header("Content-Type", "application/json;charset=UTF-8");
        long start = System.nanoTime();
        AuthStatus st;
        if ((st = this.auth.init(json)) == AuthStatus.OK && (st = this.auth.signIn()) == AuthStatus.OK) {
            ResStatus rst;
            if ((rst = this.res.init(json)) == ResStatus.OK && (rst = this.res.validate()) == ResStatus.OK) {
                this.res.save(this.auth.getId(), start);
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                Iterator var8 = this.res.findAllById(this.auth.getId()).iterator();

                while(var8.hasNext()) {
                    ResponseEntity entity = (ResponseEntity)var8.next();
                    sb.append(entity.toString());
                    sb.append(",");
                }

                sb.deleteCharAt(sb.length() - 1);
                sb.append("]");
                rb.status(200);
                rb.entity(String.format("{\"data\": %s, \"status\": \"%b\"}", sb, true));
                return rb.build();
            } else {
                return this.res.handleError(rst, rb);
            }
        } else {
            return this.auth.handleError(st, rb);
        }
    }

    @POST
    @Path("/clear")
    public Response clear(String json) throws NoSuchAlgorithmException, InvalidKeySpecException {
        ResponseBuilder rb = Response.ok();
        rb.header("Content-Type", "application/json;charset=UTF-8");
        AuthStatus st;
        if ((st = this.auth.init(json)) == AuthStatus.OK && (st = this.auth.signIn()) == AuthStatus.OK) {
            this.res.deleteAllById(this.auth.getId());
            rb.status(200);
            rb.entity(String.format("{\"status\": \"%b\"}", true));
            return rb.build();
        } else {
            return this.auth.handleError(st, rb);
        }
    }

    @POST
    @Path("/find")
    public Response find(String json) throws NoSuchAlgorithmException, InvalidKeySpecException {
        ResponseBuilder rb = Response.ok();
        rb.header("Content-Type", "application/json;charset=UTF-8");
        AuthStatus st;
        if ((st = this.auth.init(json)) == AuthStatus.OK && (st = this.auth.signIn()) == AuthStatus.OK) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Iterator var5 = this.res.findAllById(this.auth.getId()).iterator();

            while(var5.hasNext()) {
                ResponseEntity entity = (ResponseEntity)var5.next();
                sb.append(entity.toString());
                sb.append(",");
            }

            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
            rb.status(200);
            rb.entity(String.format("{\"data\": %s, \"status\": \"%b\"}", sb, true));
            return rb.build();
        } else {
            return this.auth.handleError(st, rb);
        }
    }
}
