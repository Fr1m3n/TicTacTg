package DataBase.Models.Turn;

import DataBase.HibernateSessionUtil;
import DataBase.Models.Game.GameRecord;
import DataBase.Models.User.UserRecord;
import com.google.inject.internal.asm.$Type;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TurnDAOImpl implements TurnDAO {
    public void save(TurnRecord turn) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(turn);
        transaction.commit();
        session.close();
    }

    public List<TurnRecord> getByGame(GameRecord game) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        Query query1 =  session.createQuery("select E from TurnRecord E where E.game = :game");
        query1.setParameter("game", game);
        List<TurnRecord> res  = query1.getResultList();
        session.close();
        return res;
    }

}
