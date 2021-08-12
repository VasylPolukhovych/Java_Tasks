package users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import users.dto.Employee;
import users.repository.EmployeeRepository;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsersApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private EmployeeRepository emplRep;

    @Test
    @WithMockUser(value = "nalench7@gmail.com", password = "admin", roles = {"ADMIN"})
    public void getEmployeeByEmailAPI() throws Exception {
        mvc.perform(get("/employees/getEmployee?email=user2@gmail.com"))
                .andDo(print())
                .andExpect(content().string(containsString("USER")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "nalench7@gmail.com", password = "admin", roles = {"ADMIN"})
    public void getUserByEmailAPI() throws Exception {
        mvc.perform(get("/employees/getUser?email=user1@gmail.com"))
                .andDo(print())
                .andExpect(content().string(containsString("user1@gmail.com")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "user2@gmail.com", password = "user2", roles = {"USER"})
    public void IncorrectUserGetUserByEmailAPI() throws Exception {
        mvc.perform(
                get("/employees/getUser?email=user1@gmail.com"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "nalench7@gmail.com", password = "admin", roles = {"ADMIN"})
    public void getAllUsersAPI() throws Exception {
        mvc.perform(
                get("/employees/getAllEmployees"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser(value = "nalench7@gmail.com", password = "admin", roles = {"ADMIN"})
    public void addEmployeeAPI() throws Exception {
        Employee empl = new Employee("User"
                + System.currentTimeMillis()
                + "@gmail.com"
                , "User" + System.currentTimeMillis()
                , "USER");
        mvc.perform(post("/employees/add")
                .content(objectMapper.writeValueAsString(empl))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }
}
