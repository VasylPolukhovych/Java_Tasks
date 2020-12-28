package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pizza.dto.CookedDish;
import pizza.dto.CookedDishTable;
import pizza.dto.Order;
import pizza.service.CookedDishService;
import pizza.service.ServeClient;

import java.util.List;

@Controller
@RequestMapping("pizza")

public class PizzaController {
    @Autowired
    private CookedDishService cookedDishService;
    @Autowired
    private ServeClient serveClient;

    @GetMapping("menu")
    public ResponseEntity<List<CookedDish>> getCurrentMenu() throws Exception {
        List<CookedDish> menu = cookedDishService.getMenu();
        if (menu == null) {
            return new ResponseEntity<List<CookedDish>>(menu, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<CookedDish>>(menu, HttpStatus.OK);
    }

    @PostMapping(value = "add", produces = "application/json")
    public ResponseEntity<Void> addCookedDish(@RequestBody CookedDishTable cookedDishTable, UriComponentsBuilder builder) {
        boolean flag = cookedDishService.addCookedDish(cookedDishTable);
        if (flag == false) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/add/{id}").buildAndExpand(cookedDishTable.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "addOrder", produces = "application/json")
    public ResponseEntity<Void> addOrder(@RequestBody Order order, UriComponentsBuilder builder) throws Exception {
        int orderId = serveClient.addNewOrder(order);
        if (orderId == 0) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/addOrder/{id}").buildAndExpand(orderId).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

    }

    @GetMapping("getOrder/{id}")
    public ResponseEntity<Order> getOrderDetailsById(@PathVariable("id") Integer id) throws Exception {
        Order order = serveClient.getOrderDetailsById(id);
        if (order == null) {
            return new ResponseEntity<Order>(order, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

}
