package progagoda.model.proxy;

import progagoda.model.dao.UserDao;
import progagoda.model.entity.UserEntity;
import progagoda.model.service.UserService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class UserProxy implements UserService {
    @EJB
    private UserDao dao;

    public UserProxy() {
    }

    public void save(UserEntity bean) {
        this.dao.save(bean);
    }

    public UserEntity findByLogin(String login) {
        return this.dao.findByLogin(login);
    }

    public List<UserEntity> findAll() {
        return this.dao.findAll();
    }

    public void deleteAll() {
        this.dao.deleteAll();
    }
}
