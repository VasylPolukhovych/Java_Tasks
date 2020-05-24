package pizza;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {
    private String url;
    private String user;
    private String password;
    private String driver;

    public MyConnection(String driver, String url, String user, String password) {

        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;

    }

    public Connection getConnection() throws Exception {
        // Load db Driver
        Class.forName(driver).newInstance();
        return DriverManager.getConnection(
                url, user, password);
    }

}