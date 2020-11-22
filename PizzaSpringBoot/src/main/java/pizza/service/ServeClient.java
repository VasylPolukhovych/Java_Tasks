package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.dao.*;
import pizza.dto.CookedDish;
import pizza.dto.DishInOrder;
import pizza.dto.Order;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServeClient {
    @Autowired
    private CookedDishDAO cookedDishDAO;
    @Autowired
    private OrderDAO orderDAO;
    private List<CookedDish> menu;

    public ServeClient() {
    }

    public int availableCount(String dishName) {
        return menu.stream().filter(x -> x.getNameDish().equalsIgnoreCase(dishName)).
                collect(Collectors.summingInt(p -> p.getCurrentCount()));
    }

    public CookedDish findCookedDishInMenuByName(String nameDish, List<CookedDish> someDishInMenu) throws Exception {
        return someDishInMenu.stream().filter(x -> x.getNameDish().equalsIgnoreCase(nameDish)
        ).findFirst().orElse(null);
    }

    private List<DishInOrder> checkOrder(List<DishInOrder> dishesInOrder) throws Exception {
        for (DishInOrder dio : dishesInOrder) {
            String nameDish = dio.getDish().getNameDish();
            CookedDish orderedDish;
            List<DishInOrder> orderedDishs = new ArrayList<>();
            orderedDish = findCookedDishInMenuByName(nameDish, menu);
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
                            " " + dio.getDish().getNameDish() +
                            " at the moment";
                    dio.setMessage(warningString);
                    dio.setCount(countPortions);
                }
            }
        }
        return dishesInOrder;
    }

    public void changeCount(String nameDish, int count) throws Exception {
        List<CookedDish> someDishInMenu = cookedDishDAO.getCookedDishesByName(nameDish);
        for (CookedDish cDish : someDishInMenu) {
            if (cDish.getCurrentCount() - count >= 0) {
                cookedDishDAO.setCount(cDish.getId(), cDish.getCurrentCount() - count);
                count = 0;
            } else {
                count = count - cDish.getCurrentCount();
                cookedDishDAO.setCount(cDish.getId(), 0);
            }
            if (count == 0) {
                break;
            }
        }
    }

    private void useDishsFromCurrentMenuToOrder(List<DishInOrder> dsio) throws Exception {
        if (dsio != null) {
            for (DishInOrder dishInOrder : dsio) {
                int count = dishInOrder.getCount();
                String nameDish = dishInOrder.getDish().getNameDish();
                changeCount(nameDish, count);
            }
        }
    }

    public int addNewOrder(Order order) throws Exception {
        menu = cookedDishDAO.getCurrentMenu();
        int orderId = 0;
        if (order != null) {
            List<DishInOrder> dsio = checkOrder(order.getSelectedDishes());
            useDishsFromCurrentMenuToOrder(dsio);
            orderId = orderDAO.addOrder(order);
        }
        return orderId;
    }

    public Order getOrderDetailsById(int id) throws Exception {
        Order obj = orderDAO.getOrderDetailsById(id);

        return obj;
    }
}
