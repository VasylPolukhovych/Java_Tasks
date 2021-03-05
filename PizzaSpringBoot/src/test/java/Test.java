import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import pizza.dto.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public void getMenu() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/pizza/menu";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<CookedDish[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, CookedDish[].class);
        CookedDish[] cds = responseEntity.getBody();
        for (CookedDish cd : cds) {
            System.out.println("Dish:" + cd.getNameDish() +
                    ", Count:" + cd.getCount() +
                    " ,Current Count:" + cd.getCurrentCount() +
                    " ,Date of Making:" + cd.getDateOfMaking());
        }
    }

    public void addCookedDish(String name, int count, int curCount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/pizza/add";
        CookedDishTable objCD = new CookedDishTable();
        objCD.setNameDish(name);
        objCD.setCount(count);
        objCD.setCurcount(curCount);
        HttpEntity<CookedDishTable> requestEntity = new HttpEntity<CookedDishTable>(objCD, headers);
        URI uri = restTemplate.postForLocation(url, requestEntity);
        System.out.println(uri.getPath());
    }

    public void addOrder(List<DishInOrder> dishes, int tip) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/pizza/addOrder";
        Order objOrder = new Order(dishes, tip);
        HttpEntity<Order> requestEntity = new HttpEntity<Order>(objOrder, headers);
        ResponseEntity<Order> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
                requestEntity, Order.class);
        Order order = responseEntity.getBody();
        System.out.println("Id:" + order.getId() +
                ", Tip:" + order.getTip() +
                ", Date:" + order.getDate());
        for (DishInOrder dio : order.getSelectedDishes()
                ) {
            System.out.println("Name:" + dio.getDish().getNameDish() +
                    ", Price:" + dio.getDish().getPrice().getCount() +
                    ", Count:" + dio.getCount() +
                    ", Message:" + dio.getMessage());
        }
    }

    public void getOrderDetailsById(int orderId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/pizza/getOrder/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Order> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                requestEntity, Order.class,orderId);
        Order order = responseEntity.getBody();
        System.out.println("Id:" + order.getId() +
                ", Tip:" + order.getTip() +
                ", Date:" + order.getDate());
        for (DishInOrder dio : order.getSelectedDishes()
                ) {
            System.out.println("Name:" + dio.getDish().getNameDish() +
                    ", Price:" + dio.getDish().getPrice().getCount() +
                    ", Count:" + dio.getCount() +
                    ", Message:" + dio.getMessage());
        }
    }

    public static void main(String args[]) {
        List<DishInOrder> selectedDishes = new ArrayList<>();
        int tip = 7;
        int orderId = 9;
        Test util = new Test();
        //util.getMenu();
        //util.addCookedDish("Pizza3", 10, 10);
        //util.addCookedDish("Cola", 10, 10);
        //util.getOrderDetailsById(orderId);
        //selectedDishes.add(new DishInOrder(new Dish("Cola"), 2));
        //selectedDishes.add(new DishInOrder(new Dish("Pizza3"), 2));
        //util.addOrder(selectedDishes, tip);
        util.getMenu();
        //Negative Cases
     //   util.getOrderDetailsById(10001);
        //util.addCookedDish("Colaooooooo", 15, 15);
        // util.addCookedDish("Col", 15, 15);
        //util.addCookedDish("Cola", -15, 15);
        util.addOrder(null, tip);
        util.addOrder(selectedDishes, -10);
    }

}
