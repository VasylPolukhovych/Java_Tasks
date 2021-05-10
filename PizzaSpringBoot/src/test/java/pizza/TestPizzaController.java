package pizza;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pizza.dao.CookedDishDAO;
import pizza.dao.OrderDAO;
import pizza.dto.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest

@AutoConfigureMockMvc

public class TestPizzaController {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CookedDishDAO cookedDishDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Test
    public void getCurrentMenuAPI() throws Exception {
        List<CookedDish> menu = cookedDishDAO.getCurrentMenu();
        mvc.perform(
                get("/pizza/menu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(menu)));
    }

    @Test
    public void getOrderDetailsByIdAPI() throws Exception {
        int orderId = 40;
        Order order = orderDAO.getOrderDetailsById(orderId);
        int tip = order.getTip();
        mvc.perform(
                get("/pizza/getOrder/{id}", orderId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.tip").value(tip));
    }

    @Test
    public void addCookedDishAPI() throws Exception {
        CookedDishTable cookedDishTable = new CookedDishTable();
        cookedDishTable.setNameDish("Cola");
        cookedDishTable.setCount(10);
        cookedDishTable.setCurcount(10);
        mvc.perform(post("/pizza/add")
                .content(objectMapper.writeValueAsString(cookedDishTable))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addCookedDishIncorrectInputDataAPI() throws Exception {
        CookedDishTable cookedDishTable = new CookedDishTable();
        cookedDishTable.setNameDish("Col");
        cookedDishTable.setCount(-15);
        cookedDishTable.setCurcount(15);
        mvc.perform(post("/pizza/add")
                .content(objectMapper.writeValueAsString(cookedDishTable))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void getOrderDetailsByIncorrectIdAPI() throws Exception {
        mvc.perform(
                get("/pizza/getOrder/1007"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void addOrderAPI() throws Exception {
        List<DishInOrder> selectedDishes = new ArrayList<>();
        int tip = 7;
        selectedDishes.add(new DishInOrder(new Dish("Cola"), 2));
        Order objOrder = new Order(selectedDishes, tip);
        mvc.perform(
                post("/pizza/addOrder")
                        .content(objectMapper.writeValueAsString(objOrder))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
