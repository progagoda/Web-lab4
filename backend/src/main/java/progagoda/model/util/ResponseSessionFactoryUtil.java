package progagoda.model.util;

import progagoda.model.entity.ResponseEntity;
import javax.ejb.Stateful;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@Stateful
public class ResponseSessionFactoryUtil {
    private SessionFactory sf;

    public ResponseSessionFactoryUtil() {
    }

    public SessionFactory getSessionFactory() {
        if (this.sf == null) {
            Configuration configuration = (new Configuration()).addAnnotatedClass(ResponseEntity.class).configure();
            ServiceRegistry sr = (new StandardServiceRegistryBuilder()).applySettings(configuration.getProperties()).build();
            this.sf = configuration.buildSessionFactory(sr);
        }

        return this.sf;
    }
}
