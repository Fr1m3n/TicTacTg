package Utils.di.annotations;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class Handlers {

    @Qualifier
    @Retention(RUNTIME)
    public @interface Help {}

    @Qualifier
    @Retention(RUNTIME)
    public @interface Menu {}

    @Qualifier
    @Retention(RUNTIME)
    public @interface Move {}

    @Qualifier
    @Retention(RUNTIME)
    public @interface NewGame {}

    @Qualifier
    @Retention(RUNTIME)
    public @interface Surrender {}

    @Qualifier
    @Retention(RUNTIME)
    public @interface Message {}

    @Qualifier
    @Retention(RUNTIME)
    public @interface Callback {}

}
