package Utils.di.modules;

import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new DAOModule());
        install(new ServiceModule());
        install(new HandlersModule());
        install(new KeyboardBuilderModule());
    }

}
