package Utils.di.annotations;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class KeyboardBuilders {

    @Qualifier
    @Retention(RUNTIME)
    public @interface Field {}

    @Qualifier
    @Retention(RUNTIME)
    public @interface Help {}

    @Qualifier
    @Retention(RUNTIME)
    public @interface Menu {}

    @Qualifier
    @Retention(RUNTIME)
    public @interface NewGame {}

}
