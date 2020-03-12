package demo;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import demo.resurces.PizzaResurces;

public class PizzaApplication extends Application<PizzaConfiguration> {

    public static void main(String[] args) throws Exception {
        new PizzaApplication().run(new String[]{"server", "config.yml"});
    }

    @Override
    public void run(PizzaConfiguration configuration, Environment environment) {
        final PizzaResurces resource = new PizzaResurces(configuration.getMessage(),
                configuration.getUser());
        environment.jersey().register(resource);
    }
}
