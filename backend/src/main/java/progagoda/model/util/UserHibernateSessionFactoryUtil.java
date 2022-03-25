//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package progagoda.model.util;

import progagoda.model.entity.UserEntity;
import javax.ejb.Stateful;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@Stateful
public class UserHibernateSessionFactoryUtil {
    private SessionFactory sf;

    public UserHibernateSessionFactoryUtil() {
    }

    public SessionFactory getSessionFactory() {
        if (this.sf == null) {
            Configuration configuration = (new Configuration()).addAnnotatedClass(UserEntity.class).configure();
            ServiceRegistry sr = (new StandardServiceRegistryBuilder()).applySettings(configuration.getProperties()).build();
            this.sf = configuration.buildSessionFactory(sr);
        }

        return this.sf;
    }
}

