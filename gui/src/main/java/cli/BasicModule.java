package cli;

import model.Authentication;
import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Authentication.class).toInstance(new Authentication());
    }
}
