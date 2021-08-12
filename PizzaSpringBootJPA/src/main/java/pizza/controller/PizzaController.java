package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;
import pizza.config.PizzaProperties;
import pizza.dto.CookedDish;
import pizza.dto.Dish;
import pizza.dto.Order;
import pizza.service.CookedDishService;
import pizza.service.DishService;
import pizza.service.LoadDataFromExcel;
import pizza.service.ServeClient;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    LoadDataFromExcel loadDataFromExcel;

    @Value(value = "${pizza.path-for-loading-from-excel}")
    String inFolder;

    @GetMapping("getDish/{name}")
    public ResponseEntity<Object> getOrderDetailsById(@PathVariable("name")
                                                              String name) {
        Dish ds = dishService.findByName(name);
        if (ds == null) {
            return new ResponseEntity<Object>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Object>(ds, HttpStatus.OK);
    }


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
    public ResponseEntity<Object> addCookedDish(@Valid @RequestBody CookedDish cookedDish,
                                                UriComponentsBuilder builder) {
        CookedDish result = new CookedDish();
        result = cookedDishService.addCookedDish(cookedDish);
        if (result == null) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(result, HttpStatus.CREATED);
    }

    @PostMapping(value = "addFromExcel/{fileName}")
    public ResponseEntity<Object> addCookedDishFromExcel(@PathVariable("fileName") String fileName
    ) throws IOException, IllegalAccessException {
        List<CookedDish> result = new ArrayList<>();
        result = loadDataFromExcel.readFromExcel(inFolder + fileName);
        if (result == null) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(result, HttpStatus.CREATED);
    }

    @GetMapping("getOrder/{id}")
    public ResponseEntity<Object> getOrderDetailsById(@PathVariable("id")
                                                      @Min(value = 1, message = "Error. Id must be greater than or equal to 1")
                                                      @Max(value = 1000, message = "Error. Id must be lower than or equal to 1000") Long id) {
        Order order = serveClient.getOrderDetailsById(id);
        if (order == null) {
            return new ResponseEntity<Object>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Object>(order, HttpStatus.OK);
    }

    @PostMapping(value = "addOrder", produces = "application/json")
    public ResponseEntity<Object> addOrder(@Valid @RequestBody Order order,
                                           UriComponentsBuilder builder, Errors err) {
        Order result = new Order();
        result = serveClient.addNewOrder(order);
        if (result == null || err.hasErrors()) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(result, HttpStatus.CREATED);

    }
}
