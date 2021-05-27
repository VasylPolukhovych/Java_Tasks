package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;
import pizza.dto.CookedDishTable;
import pizza.dto.Dish;
import pizza.dto.Order;
import pizza.service.CookedDishService;
import pizza.service.DishService;
import pizza.service.ServeClient;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Controller
@RequestMapping("pizza")
@Validated
public class PizzaController {

    @Autowired
    private DishService dishService;
    @Autowired
    private CookedDishService cookedDishService;
    @Autowired
    private ServeClient serveClient;

    @GetMapping("menu")
    public ResponseEntity<List<Dish>> getCurrentMenu() {
        List<Dish> menu = dishService.getMenu();
        if (menu == null) {
            return new ResponseEntity<List<Dish>>(menu, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Dish>>(menu, HttpStatus.OK);
    }

    @GetMapping("cookeddishes")
    public ResponseEntity<List<Dish>> getCookedDishes() {
        List<Dish> cookedDishes = dishService.getAll();
        if (cookedDishes == null) {
            return new ResponseEntity<List<Dish>>(cookedDishes, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Dish>>(cookedDishes, HttpStatus.OK);
    }

    @PostMapping(value = "add", produces = "application/json")
    public ResponseEntity<Object> addCookedDish(@Valid @RequestBody CookedDishTable cookedDishTable,
                                                UriComponentsBuilder builder) {
        CookedDishTable result = new CookedDishTable();
        result = cookedDishService.addCookedDish(cookedDishTable);
        if (result == null) {
            return new ResponseEntity<Object>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Object>(result, HttpStatus.CREATED);
    }


    @GetMapping("getOrder/{id}")
    public ResponseEntity<Order> getOrderDetailsById(@PathVariable("id")
                                                     @Min(value = 1, message = "id must be greater than or equal to 1")
                                                     @Max(value = 1000, message = "id must be lower than or equal to 1000") Long id) {
        Order order = serveClient.getOrderDetailsById(id);
        if (order == null) {
            return new ResponseEntity<Order>(order, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @PostMapping(value = "addOrder", produces = "application/json")
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order,
                                          UriComponentsBuilder builder) {
        Order result = new Order();
        result = serveClient.addNewOrder(order);
        if (result == null) {
            return new ResponseEntity<Order>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Order>(result, HttpStatus.CREATED);

    }
}
