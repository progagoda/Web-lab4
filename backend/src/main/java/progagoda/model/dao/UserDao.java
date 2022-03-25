package progagoda.model.dao;

import progagoda.model.entity.UserEntity;
import progagoda.model.service.UserService;
import progagoda.model.util.UserHibernateSessionFactoryUtil;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Stateless
@LocalBean
public class UserDao implements UserService {
    @EJB
    private UserHibernateSessionFactoryUtil hsfu;

    public UserDao() {
    }

    public void save(UserEntity bean) {
        Session session = this.hsfu.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(bean);
        tx1.commit();
        session.close();
    }

    public UserEntity findByLogin(String login) {
        Session session = this.hsfu.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query<UserEntity> userQuery = session.createQuery("FROM UserEntity u WHERE u.login = ?1", UserEntity.class).setParameter(1, login);
        UserEntity user = (UserEntity)userQuery.uniqueResult();
        tx1.commit();
        session.close();
        return user;
    }

    public List<UserEntity> findAll() {
        Session session = this.hsfu.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<UserEntity> list = session.createQuery("FROM UserEntity ").getResultList();
        tx1.commit();
        session.close();
        return list;
    }

    public void deleteAll() {
        Session session = this.hsfu.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.createQuery("DELETE FROM UserEntity").executeUpdate();
        tx1.commit();
        session.close();
    }
}
