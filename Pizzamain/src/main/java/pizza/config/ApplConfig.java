package pizza.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pizza.dao.CurrentMenuDAO;
import pizza.dao.OrderDAO;
import pizza.input.InputData;
import pizza.input.InputDataOfOrder;
import pizza.output.Reports;
import pizza.service.ServeClient;

@Configuration
@ComponentScan(basePackages = "pizza.dao")
public class ApplConfig {

    @Bean(name = "dataSource")
    public PGSimpleDataSource getDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{"localhost"});
        ds.setDatabaseName("Testdb");
        ds.setUser("postgres");
        ds.setPassword("admin");
        return ds;
    }

    @Bean(name = "input")
    public InputData getInput() {
        return new InputDataOfOrder();
    }

    @Bean(name = "report")
    public Reports getReports() {
        return new Reports();
    }


    @Bean(name = "serveClient")
    @Scope(scopeName = "prototype")
    public ServeClient getServeClient(InputData input, Reports report, OrderDAO orderDAO
            , CurrentMenuDAO currentMenuDAO) throws Exception {
        return new ServeClient(input, report, orderDAO, currentMenuDAO);
    }
}
