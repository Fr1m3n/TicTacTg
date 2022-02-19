package DataBase.Models.User;

import DataBase.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAOImpl implements UserDAO {
    @Override
    public UserRecord getById(Long id) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        UserRecord user = session.get(UserRecord.class, id);

        session.close();

        return user;
    }

    @Override
    public void save(UserRecord user) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(UserRecord user) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void save(Long id) {
        UserRecord user = new UserRecord(id);
        this.save(user);
    }

}
