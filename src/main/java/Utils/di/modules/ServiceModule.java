package Utils.di.modules;

import DataBase.Models.Game.GameService;
import DataBase.Models.Game.GameServiceImpl;
import DataBase.Models.Turn.TurnService;
import DataBase.Models.Turn.TurnServiceImpl;
import DataBase.Models.User.UserService;
import DataBase.Models.User.UserServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(GameService.class)
                .to(GameServiceImpl.class)
                .in(Scopes.SINGLETON);

        bind(TurnService.class)
                .to(TurnServiceImpl.class)
                .in(Scopes.SINGLETON);

        bind(UserService.class)
                .to(UserServiceImpl.class)
                .in(Scopes.SINGLETON);
    }
}
