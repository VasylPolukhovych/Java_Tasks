package users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import users.repository.EmployeeRepository;
import users.service.EmployeeDetailsService;

@Configuration
public class EmployeeSecurityConfig extends WebSecurityConfigurerAdapter {
    final private EmployeeRepository employeeRepository;

    public EmployeeSecurityConfig(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/**").hasRole("ADMIN")
                .and()
                .csrf()
                .disable()
                .formLogin().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                new EmployeeDetailsService(this.employeeRepository));
    }

}
