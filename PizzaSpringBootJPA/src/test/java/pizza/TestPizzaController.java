package pizza;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pizza.dto.*;
import pizza.repository.DishRep;
import pizza.repository.OrderRep;

import java.time.LocalDate;
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
    private DishRep dishRep;
    @Autowired
    private OrderRep orderRep;

    @Test
    public void getDishByNameAPI() throws Exception {
        String name = "Pizza3";
        Dish ds = dishRep.findByName(name).orElse(null);
        mvc.perform(
                get("/pizza/getDish/{name}", name))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void getCurrentMenuAPI() throws Exception {
        mvc.perform(
                get("/pizza/menu"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCookedDishesAPI() throws Exception {
        List<Dish> dishes = dishRep.findAll();
        mvc.perform(
                get("/pizza/cookeddishes"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOrderDetailsByIdAPI() throws Exception {
        Long orderId = Long.valueOf(41);
        Order order = orderRep.findById(orderId).orElse(null);
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
        Dish dish = dishRep.findByName("Cola").orElse(null);
        CookedDish cookedDish = new CookedDish();
        cookedDish.setIdDish(dish.getId());
        cookedDish.setCount(13);
        cookedDish.setCurcount(13);
        cookedDish.setDateOfMaking(LocalDate.now());
        mvc.perform(post("/pizza/add")
                .content(objectMapper.writeValueAsString(cookedDish))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addCookedDishIncorrectInputDataAPI() throws Exception {
        Dish dish = dishRep.findByName("Cola").orElse(null);

        CookedDish cookedDish = new CookedDish();
        cookedDish.setIdDish(1L);
        cookedDish.setCount(-15);
        mvc.perform(post("/pizza/add")
                .content(objectMapper.writeValueAsString(cookedDish))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
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
        selectedDishes.add(new DishInOrder("Cola", 1));
        selectedDishes.add(new DishInOrder("Pizza1", 1));
        Order objOrder = new Order(selectedDishes, LocalDate.now(), tip);
        mvc.perform(
                post("/pizza/addOrder")
                        .content(objectMapper.writeValueAsString(objOrder))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
