package progagoda.model.dao;

import progagoda.model.entity.ResponseEntity;
import progagoda.model.service.ResponseService;
import progagoda.model.util.ResponseSessionFactoryUtil;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Stateless
@LocalBean
public class ResponseDao implements ResponseService {
    @EJB
    private ResponseSessionFactoryUtil hsfu;

    public ResponseDao() {
    }

    public void save(ResponseEntity bean) {
        Session session = this.hsfu.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(bean);
        tx1.commit();
        session.close();
    }

    public List<ResponseEntity> findAllById(Long id) {
        Session session = this.hsfu.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query<ResponseEntity> resQuery = session.createQuery("FROM ResponseEntity r WHERE r.userId = ?1", ResponseEntity.class).setParameter(1, id);
        List<ResponseEntity> res = resQuery.getResultList();
        tx1.commit();
        session.close();
        return res;
    }

    public void deleteAllById(Long id) {
        Session session = this.hsfu.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.createQuery("DELETE FROM ResponseEntity r WHERE r.userId = ?1").setParameter(1, id).executeUpdate();
        tx1.commit();
        session.close();
    }
}
