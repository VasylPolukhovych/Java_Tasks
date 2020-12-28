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
            System.out.println("Dish:" + cd.getNameDish() + ", Count:" + cd.getCount() + " ,Current Count:" + cd.getCurrentCount()
                    + " ,Date of Making:" + cd.getDateOfMaking());
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

    public void addOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/pizza/addOrder";
        List<DishInOrder> selectedDishes = new ArrayList<>();
        selectedDishes.add(new DishInOrder(new Dish("Pizza3"), 34));
        selectedDishes.add(new DishInOrder(new Dish("Cola"), 4));
        int tip = 3;
        Order objOrder = new Order(selectedDishes, tip);
        HttpEntity<Order> requestEntity = new HttpEntity<Order>(objOrder, headers);
        URI uri = restTemplate.postForLocation(url, requestEntity);
        System.out.println(uri.getPath());
    }

    public void getOrderDetailsById() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/pizza/getOrder/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Order> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Order.class, 7);
        Order order = responseEntity.getBody();
        System.out.println("Id:" + order.getId() + ", Tip:" + order.getTip()
                + ", Date:" + order.getDate());
        for (DishInOrder dio : order.getSelectedDishes()
                ) {
            System.out.println("Name:" + dio.getDish().getNameDish() + ", Price:" + dio.getDish().getPrice().getCount()
                    + ", Count:" + dio.getCount() + ", Message:" + dio.getMessage());
        }
    }

    public static void main(String args[]) {
        Test util = new Test();
        util.getMenu();
        util.addCookedDish("Pizza3", 10, 10);
        util.addCookedDish("Cola", 10, 10);
        util.addOrder();
        util.getOrderDetailsById();
        util.getMenu();
    }

}
