package users.dto;

import javax.persistence.*;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

@Entity(name = "users")
public class Employee {
    @Id
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String role = "USER";
    private LocalDate birthday;
    @Column(name = "user_nm")
    private String name;

    public Employee() {
    }

    public Employee(String email, String password, String role) {
        this.email = email;
        this.role = role;
        this.password = password;
    }


    public Employee(String email, String name, String password, LocalDate birthday, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.birthday = birthday;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

