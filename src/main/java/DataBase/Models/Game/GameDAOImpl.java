package DataBase.Models.Game;

import DataBase.HibernateSessionUtil;
import DataBase.Models.User.UserRecord;
import TicTacToe.GameState;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class GameDAOImpl implements GameDAO {
    public void save(GameRecord game) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(game);
        transaction.commit();
        session.close();
    }

    public List<GameRecord> getByPlayer(UserRecord user) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        Query<GameRecord> query = session.createQuery("select E from GameRecord E where firstPlayer = :pl1 or secondPlayer =:pl1", GameRecord.class);
        query.setParameter("pl1", user);
        List<GameRecord> res = query.getResultList();
        session.close();
        return res;
    }

    public GameRecord getByPlayerAndState(UserRecord user, GameState state) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        Query<GameRecord> query = session.createQuery("select E from GameRecord E where (firstPlayer = :pl1 or secondPlayer =:pl1) and E.state = :st", GameRecord.class);
        query.setParameter("pl1", user);
        query.setParameter("st", state);
        GameRecord res = query.getSingleResult();
        session.close();
        return res;
    }

    @Override
    public GameRecord getByInviteCode(String inviteCode) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        Query<GameRecord> query = session.createQuery("select E from GameRecord E where E.inviteCode=:code", GameRecord.class);
        query.setParameter("code", inviteCode);
        GameRecord res = query.getSingleResult();
        session.close();
        return res;
    }

    @Override
    public Long getTurnsCount(GameRecord game) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        Query<Long> query = session.createQuery("select count(E) from TurnRecord E where E.game=:game", Long.class);
        query.setParameter("game", game);
        Long res = query.getSingleResult();
        session.close();
        return res;
    }

    @Override
    public void update(GameRecord game) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(game);
        transaction.commit();
        session.close();
    }


}
