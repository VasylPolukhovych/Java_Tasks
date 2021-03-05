package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import pizza.dto.CookedDish;
import pizza.dto.CookedDishTable;
import pizza.dto.Order;
import pizza.service.CookedDishService;
import pizza.service.ServeClient;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("pizza")
@Validated
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
    public ResponseEntity<Object> addCookedDish(@Valid @RequestBody CookedDishTable cookedDishTable, UriComponentsBuilder builder) {

        boolean flag = cookedDishService.addCookedDish(cookedDishTable);
        if (flag == false) {
            return new ResponseEntity<Object>(HttpStatus.CONFLICT);
        }

        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cookedDishTable.getId()).toUri();
        return ResponseEntity.created(url).build();
    }

    @PostMapping(value = "addOrder", produces = "application/json")
    public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order, UriComponentsBuilder builder) throws Exception {
        int orderId = serveClient.addNewOrder(order);
        if (orderId == 0) {
            return new ResponseEntity<Order>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("pizza/getOrder/{id}").buildAndExpand(orderId).toUri());
        RestTemplate restTemplate = new RestTemplate();
        URI uri = headers.getLocation();
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Order> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Order.class);
        Order ord = responseEntity.getBody();
        return new ResponseEntity<Order>(ord, HttpStatus.CREATED);
    }

    @GetMapping("getOrder/{id}")
    public ResponseEntity<Order> getOrderDetailsById(@PathVariable("id")
                                                     @Min(value = 1, message = "id must be greater than or equal to 1")
                                                     @Max(value = 1000, message = "id must be lower than or equal to 1000") Integer id) throws Exception {
        Order order = serveClient.getOrderDetailsById(id);
        if (order == null) {
            return new ResponseEntity<Order>(order, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

}
