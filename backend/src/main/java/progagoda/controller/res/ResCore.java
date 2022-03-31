package progagoda.controller.res;

import com.google.gson.Gson;
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

    public ResCore() {
    }
    public Float[] getParam(String json){
        try {
            JSONObject object = new JSONObject(json);
            float x = Float.parseFloat(object.getString("x"));
            float y = Float.parseFloat(object.getString("y"));
            float r = Float.parseFloat(object.getString("r"));
            Float result [] =new Float[] {x,y,r};
            return result;
        } catch (JSONException var3) {
            this.logger.error("JSON error while parsing \"{}\"", json, var3);
        } catch (Exception var4) {
            this.logger.error("Undefined error on request \"{}\"", json, var4);
        }
        return null;
    }
    public ResStatus init(String json) {
        try {
            JSONObject object = new JSONObject(json);
            float x = Float.parseFloat(object.getString("x"));
            float y = Float.parseFloat(object.getString("y"));
            float r = Float.parseFloat(object.getString("r"));
            return ResStatus.OK;
        } catch (JSONException var3) {
            this.logger.error("JSON error while parsing \"{}\"", json, var3);
        } catch (Exception var4) {
            this.logger.error("Undefined error on request \"{}\"", json, var4);
        }

        return ResStatus.UNDEFINED_ERROR;
    }

    public ResStatus validate(Float[] result) {
        float y = result[1];
        float r =result[2];
        boolean y_valid = y >= -5.0F && y <= 5.0F;
        boolean r_valid = r == 1.0F || r == 2.0F || r == 3.0F;
        return y_valid && r_valid ? ResStatus.OK : ResStatus.VALIDATION_FAILED;
    }

    public void save(long userId, long start, Float [] result) {
        ResponseEntity entity = new ResponseEntity(userId, result[0], result[1], result[2], (System.nanoTime() - start) / 1000L, AreaCheckUtil.isIn(result[0], result[1], result[2]), AreaCheckUtil.left(result[0]), AreaCheckUtil.top(result[1]));
        this.response.save(entity);
    }

    public List<ResponseEntity> findAllById(Long id) {
        return this.response.findAllById(id);
    }

    public void deleteAllById(Long id) {
        this.response.deleteAllById(id);
    }

    public Response handleError(ResStatus st, ResponseBuilder rb) {
        StringBuilder status = new StringBuilder();
        status.append("false");
        Gson gson = new Gson();
        rb.entity(gson.toJson(st.getDescription()));
        rb.entity(gson.toJson(status));
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

}
