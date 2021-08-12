package users.repository;

import org.springframework.data.repository.CrudRepository;
import users.dto.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    Employee findByEmailIgnoreCase(String email);

    List<Employee> findAll();

    }
