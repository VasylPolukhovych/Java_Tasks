package users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;
import users.dto.Employee;
import users.service.EmployeeDetailsService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    private EmployeeDetailsService employeeDetailsService;

    @PostMapping(value = "add", produces = "application/json")
    public ResponseEntity<Object> addEmployee(@Valid @RequestBody Employee employee,
                                              UriComponentsBuilder builder) {
        Employee result = employeeDetailsService.addEmployee(employee);
        if (result == null) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(result, HttpStatus.CREATED);
    }

    @GetMapping("/getUser")
    public ResponseEntity<Object> getUser(@RequestParam(name = "email") String userName) {
        UserDetails userDetails = employeeDetailsService.loadUserByUsername(userName);
        if (userDetails == null) {
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(userDetails, HttpStatus.OK);
    }

    @GetMapping("getEmployee")
    public ResponseEntity<Object> getEmployee(@RequestParam(name = "email")
                                                      String userName) {
        Employee employee = employeeDetailsService.getEmployeeByUsername(userName);
        if (employee == null) {
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ResponseEntity<Employee> responseEntity = new ResponseEntity<Employee>(employee, HttpStatus.OK);
        return new ResponseEntity<Object>(employee, HttpStatus.OK);
    }

    @GetMapping("getAllEmployees")
    public ResponseEntity<Object> getUser() {
        List<Employee> emplList = employeeDetailsService.findAll();
        if (emplList == null) {
            return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(emplList, HttpStatus.OK);
    }
}
