package DataBase;

import DataBase.Models.Game.GameRecord;
import DataBase.Models.Turn.TurnRecord;
import DataBase.Models.User.UserRecord;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateSessionUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null){
            Configuration configuration = new Configuration().configure();
            
            configuration.addAnnotatedClass(GameRecord.class);
            configuration.addAnnotatedClass(TurnRecord.class);
            configuration.addAnnotatedClass(UserRecord.class);
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(registryBuilder.build());
        }
        return sessionFactory;
    }
}
