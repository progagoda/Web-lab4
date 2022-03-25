package progagoda.controller.res;

import progagoda.model.entity.ResponseEntity;
import progagoda.model.proxy.ResponseProxy;
import java.util.List;
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
public class ResCore {
    private final Logger logger = LoggerFactory.getLogger(ResCore.class);
    @EJB
    private ResponseProxy response;
    private float x;
    private float y;
    private float r;

    public ResCore() {
    }

    public ResStatus init(String json) {
        try {
            JSONObject object = new JSONObject(json);
            this.x = Float.parseFloat(object.getString("x"));
            this.y = Float.parseFloat(object.getString("y"));
            this.r = Float.parseFloat(object.getString("r"));
            return ResStatus.OK;
        } catch (JSONException var3) {
            this.logger.error("JSON error while parsing \"{}\"", json, var3);
        } catch (Exception var4) {
            this.logger.error("Undefined error on request \"{}\"", json, var4);
        }

        return ResStatus.UNDEFINED_ERROR;
    }

    public ResStatus validate() {
        boolean y_valid = this.y >= -5.0F && this.y <= 5.0F;
        boolean r_valid = this.r == 1.0F || this.r == 2.0F || this.r == 3.0F;
        return y_valid && r_valid ? ResStatus.OK : ResStatus.VALIDATION_FAILED;
    }

    public void save(long userId, long start) {
        ResponseEntity entity = new ResponseEntity(userId, this.x, this.y, this.r, (System.nanoTime() - start) / 1000L, AreaCheckUtil.isIn(this.x, this.y, this.r), AreaCheckUtil.left(this.x), AreaCheckUtil.top(this.y));
        this.response.save(entity);
    }

    public List<ResponseEntity> findAllById(Long id) {
        return this.response.findAllById(id);
    }

    public void deleteAllById(Long id) {
        this.response.deleteAllById(id);
    }

    public Response handleError(ResStatus st, ResponseBuilder rb) {
        rb.entity(String.format("{\"data\": \"%s\", \"status\": \"%b\"}", st.getDescription(), false));
        switch(st) {
            case VALIDATION_FAILED:
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

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getR() {
        return this.r;
    }
}
