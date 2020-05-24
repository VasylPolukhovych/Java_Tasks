package demo;

import demo.dao.IPizzaDAO;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

import demo.resurces.PizzaResurces;
import org.skife.jdbi.v2.DBI;

public class PizzaApplication extends Application<PizzaConfiguration> {

    public static void main(String[] args) throws Exception {
        new PizzaApplication().run(new String[]{"server", "config.yml"});
    }

    @Override
    public void run(PizzaConfiguration configuration, Environment environment) {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        final IPizzaDAO dao = jdbi.onDemand(IPizzaDAO.class);
        environment.jersey().register(new PizzaResurces(dao));

    }

}
