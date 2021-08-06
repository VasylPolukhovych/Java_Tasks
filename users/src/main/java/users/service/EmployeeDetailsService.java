package users.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import users.dto.Employee;
import users.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    private EmployeeRepository employeeRepository;

    public EmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final Employee empl =
                    this.employeeRepository.findByEmailIgnoreCase(username);
            if (empl != null) {
                PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                String password = passwordEncoder.encode(empl.getPassword());
                return User.withUsername(empl.getEmail()).password(password)
                        .roles(empl.getRole()).build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        throw new UsernameNotFoundException(username);
    }

    public Employee getEmployeeByUsername(String username) {
        try {
            final Employee empl =
                    this.employeeRepository.findByEmailIgnoreCase(username);
            if (empl != null) {
                return empl;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        throw new UsernameNotFoundException(username);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
