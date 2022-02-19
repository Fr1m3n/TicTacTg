package Utils.di.modules;

import DataBase.Models.Game.GameDAO;
import DataBase.Models.Game.GameDAOImpl;
import DataBase.Models.Turn.TurnDAO;
import DataBase.Models.Turn.TurnDAOImpl;
import DataBase.Models.User.UserDAO;
import DataBase.Models.User.UserDAOImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class DAOModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(GameDAO.class)
                .to(GameDAOImpl.class)
                .in(Scopes.SINGLETON);

        bind(TurnDAO.class)
                .to(TurnDAOImpl.class)
                .in(Scopes.SINGLETON);

        bind(UserDAO.class)
                .to(UserDAOImpl.class)
                .in(Scopes.SINGLETON);
    }
}
