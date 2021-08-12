package pizza.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pizza.authentication")
public class PizzaProperties {
    private String findAdminByEmailUri;
    private String findUserByEmailUri;
    private String username;
    private String password;

    public PizzaProperties() {
    }

    public String getFindAdminByEmailUri() {
        return findAdminByEmailUri;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFindUserByEmailUri() {
        return findUserByEmailUri;
    }

    public void setFindAdminByEmailUri(String findAdminByEmailUri) {
        this.findAdminByEmailUri = findAdminByEmailUri;
    }

    public void setFindUserByEmailUri(String findUserByEmailUri) {
        this.findUserByEmailUri = findUserByEmailUri;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
