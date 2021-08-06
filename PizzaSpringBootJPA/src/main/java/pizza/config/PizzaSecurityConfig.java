package pizza.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pizza.dto.Employee;
import java.util.HashMap;
import java.util.Map;

@EnableConfigurationProperties(PizzaProperties.class)
@Configuration
public class PizzaSecurityConfig extends WebSecurityConfigurerAdapter {

    private RestTemplate restTemplate;
    private PizzaProperties pizzaProperties;
    private UriComponentsBuilder uriBuilder;

    public PizzaSecurityConfig(RestTemplateBuilder restTemplateBuilder,
                               PizzaProperties pizzaProperties) {
        this.pizzaProperties = pizzaProperties;
        this.restTemplate = restTemplateBuilder.basicAuthentication(
                pizzaProperties.getUsername(),
                pizzaProperties.getPassword()).build();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
                    try {
                        uriBuilder = UriComponentsBuilder
                                .fromUriString(pizzaProperties.getFindUserByEmailUri())
                                .queryParam("email", username);
                        Map<String, String> params = new HashMap<>();
                        params.put("email", username);
                        Employee empl = restTemplate
                                .getForObject(uriBuilder.toUriString(), Employee.class, params);
                        if (empl != null) {
                            PasswordEncoder encoder = PasswordEncoderFactories
                                    .createDelegatingPasswordEncoder();
                            String password = encoder.encode(empl.getPassword());
                            return User.withUsername(empl.getEmail())
                                    .password(password).
                                            roles(empl.getRole())
                                    .build();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    throw new UsernameNotFoundException(username);
                }
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/**").hasAnyRole("USER", "ADMIN")
                .and().csrf().disable().formLogin().disable();
    }
}