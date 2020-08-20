package pizza.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pizza.aspect.LogAndChecks;
import pizza.dao.CurrentMenuDAO;
import pizza.dao.OrderDAO;
import pizza.dao.UserDAO;
import pizza.input.InputData;
import pizza.input.InputDataOfOrder;
import pizza.output.Reports;
import pizza.service.CheckUser;
import pizza.service.ServeClient;

@Configuration
@ComponentScan(basePackages = "pizza.dao")
@EnableAspectJAutoProxy
@EnableTransactionManagement
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

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(getDataSource());
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

    @Bean(name = "checkUser")
    public CheckUser getCheckUser(InputData input, UserDAO userDAO) {
        return new CheckUser(input, userDAO);

    }

    @Bean(name = "logAndChecks")
    public LogAndChecks getLogAndChecks() {
        return new LogAndChecks();
    }

}
