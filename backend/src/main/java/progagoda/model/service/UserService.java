package progagoda.model.service;

import progagoda.model.entity.UserEntity;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserService {
    void save(UserEntity var1);

    UserEntity findByLogin(String var1);

    List<UserEntity> findAll();

    void deleteAll();
}

