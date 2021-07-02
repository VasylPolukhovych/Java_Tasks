package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.dto.CookedDish;
import pizza.dto.Dish;
import pizza.dto.DishInOrder;
import pizza.dto.Order;
import pizza.repository.CookedDishRep;
import pizza.repository.DishInOrederRep;
import pizza.repository.DishRep;
import pizza.repository.OrderRep;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServeClient {
    @Autowired
    private DishRep dishRep;
    @Autowired
    private OrderRep orderRep;
    @Autowired
    private CookedDishRep cookedDishRep;
    @Autowired
    private DishInOrederRep dishInOrederRep;
    @Autowired
    private DishService dishService;
    private List<Dish> menu;

    public ServeClient() {
    }

    public int availableCount(String dishName) {
        return menu.stream().filter(x -> x.getName().equalsIgnoreCase(dishName)).
                map(z -> z.getCookedDish().stream().mapToInt(y -> y.getCurcount()).sum())
                .collect(Collectors.summingInt(r -> r));
    }

    public Dish findCookedDishInMenuByName(String nameDish) {
        return menu.stream().filter(x -> x.getName().equalsIgnoreCase(nameDish))
                .findFirst().orElse(null);
    }

    private List<DishInOrder> checkOrder(List<DishInOrder> dishesInOrder) {
        for (DishInOrder dio : dishesInOrder) {
            String nameDish = dio.getNameDish();
            Dish orderedDish;
            List<DishInOrder> orderedDishs = new ArrayList<>();
            orderedDish = findCookedDishInMenuByName(nameDish);
            if (orderedDish == null) {
                String newString = "Sorry, unfortunately but we don't have " +
                        nameDish + " at the moment";
                dio.setMessage(newString);
                dio.setCount(0);
            } else {
                int countPortions = availableCount(nameDish);
                if (countPortions < dio.getCount()) {
                    String warningString = "Sorry, unfortunately but we have only " +
                            ((Integer) countPortions).toString() +
                            " " + dio.getNameDish() +
                            " at the moment";
                    dio.setMessage(warningString);
                    dio.setCount(countPortions);
                }
            }
        }
        return dishesInOrder;
    }

    public void changeCount(String nameDish, int count) {
        Dish someDishInMenu = dishService.findCookedDishByName(nameDish);
        for (CookedDish cDish : someDishInMenu.getCookedDish()) {
            if (cDish.getCurcount() - count >= 0) {
                cookedDishRep.setCookedDishCount(cDish.getCurcount() - count, cDish.getId());
                count = 0;
            } else {
                count = count - cDish.getCurcount();
                cookedDishRep.setCookedDishCount(0, cDish.getId());
            }
            if (count == 0) {
                break;
            }
        }
    }

    private void useDishsFromCurrentMenuToOrder(List<DishInOrder> dsio) {
        if (dsio != null) {
            for (DishInOrder dishInOrder : dsio) {
                Integer count = dishInOrder.getCount();
                String nameDish = dishInOrder.getNameDish();
                changeCount(nameDish, count);
            }
        }
    }

    public Order addNewOrder(Order order) {
        menu = dishService.getMenu();
        Long newOrderId;
        if (order != null) {
            Order newOrder = new Order(LocalDate.now(), order.getTip());

            List<DishInOrder> dsio = checkOrder(order.getSelectedDishes());
            for (DishInOrder dishInOrder : dsio
            ) {
                newOrder.addDishInOrder(dishInOrder);

            }


            newOrder = orderRep.save(newOrder);
            useDishsFromCurrentMenuToOrder(dsio);
            return newOrder;
        } else {
            return null;
        }
    }

    public Order getOrderDetailsById(Long id) {
        Order obj = orderRep.findById(id).orElse(null);
        return obj;
    }
}
