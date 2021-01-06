package Injectors;

import com.google.inject.name.Names;
import controller.Communicator;
import model.Authentication;
import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("apiBaseUrl")).to("http://localhost:8080/api/");
        Authentication testAuth = new Authentication();
//        testAuth.addRole("ADMIN");
//        testAuth.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjA5MzIxOTExLCJpYXQiOjE2MDkyODU5MTF9._GX-DioGxj695c-LdOSpdslsYY_kwG4XkTBLJ-4oDsQ");
        bind(Authentication.class).toInstance(testAuth);
        bind(Communicator.class).toInstance(new Communicator());
    }
}
