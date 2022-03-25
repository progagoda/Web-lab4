package progagoda.model.service;

import progagoda.model.entity.ResponseEntity;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ResponseService {
    void save(ResponseEntity var1);

    List<ResponseEntity> findAllById(Long var1);

    void deleteAllById(Long var1);
}
