package pizza.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pizza.dao.CurrentMenuDAO;
import pizza.dto.CookedDish;
import pizza.input.InputData;
import pizza.input.InputDataOfOrder;
import pizza.output.Reports;
import pizza.service.ServeClient;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "pizza.dao")
public class ApplConfig {

    @Bean(name = "conn")
    public Connection getConn() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{"localhost"});
        ds.setDatabaseName("Testdb");
        ds.setUser("postgres");
        ds.setPassword("admin");
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            System.out.println("SQL Error code " + e.getErrorCode() + ". " + e.getMessage());
        }
        return connection;
    }

    @Bean(name = "input")
    public InputData getInput() {
        return new InputDataOfOrder();
    }

    @Bean(name = "report")
    public Reports getReports() {
        return new Reports();
    }

    @Bean(name = "menuDAO")
    public CurrentMenuDAO getCurrentMenu() {
        return new CurrentMenuDAO();
    }

    @Bean(name = "serveClient")
    @Scope(scopeName = "prototype")
    public ServeClient getServeClient(InputData input, List<CookedDish> menu) {
        return new ServeClient(input, menu);
    }
}
