package progagoda.model.proxy;

import progagoda.model.dao.ResponseDao;
import progagoda.model.entity.ResponseEntity;
import progagoda.model.service.ResponseService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ResponseProxy implements ResponseService {
    @EJB
    private ResponseDao dao;

    public ResponseProxy() {
    }

    public void save(ResponseEntity bean) {
        this.dao.save(bean);
    }

    public List<ResponseEntity> findAllById(Long id) {
        return this.dao.findAllById(id);
    }

    public void deleteAllById(Long id) {
        this.dao.deleteAllById(id);
    }
}
