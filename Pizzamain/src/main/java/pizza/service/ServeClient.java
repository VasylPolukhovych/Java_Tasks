package pizza.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pizza.dao.*;
import pizza.dto.CookedDish;
import pizza.dto.DishInOrder;
import pizza.dto.Order;
import pizza.input.InputData;
import pizza.output.Reports;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class ServeClient {
    private InputData inputData;
    private List<CookedDish> menu;
    private Reports report;
    private Order order;
    private CurrentMenuDAO menuDAO;
    private OrderDAO orderDAO;

    public ServeClient(InputData inputData, Reports report, OrderDAO orderDAO, CurrentMenuDAO menuDAO) throws Exception {
        this.inputData = inputData;
        this.report = report;
        this.orderDAO = orderDAO;
        this.menuDAO = menuDAO;
        this.menu = menuDAO.getCurrentMenu();
    }


    public int availableCount(String dishName) {
        return menu.stream().filter(x -> x.getNameDish().equalsIgnoreCase(dishName)).
                collect(Collectors.summingInt(p -> p.getCurrentCount()));
    }

    private int calculateCount(int totalCount, int neededCount, String name) {
        if (totalCount >= neededCount) {
            return neededCount;
        } else {
            StringJoiner warningString = new StringJoiner(" ");
            warningString.add("Sorry, unfortunately but we have only");
            warningString.add(((Integer) totalCount).toString());
            warningString.add(name);
            warningString.add("at the moment");
            System.out.println(warningString);
            return totalCount;
        }
    }

    public CookedDish findCookedDishInMenuByName(String nameDish, List<CookedDish> someDishInMenu) throws Exception {
        return someDishInMenu.stream().filter(x -> x.getNameDish().equalsIgnoreCase(nameDish)
        ).findFirst().orElse(null);
    }

    private Order fillOrder(int tip) throws Exception {
        CookedDish orderedDish;
        List<DishInOrder> orderedDishs = new ArrayList<>();
        for (Map.Entry<String, Long> orderDetail : inputData.inputDetails().entrySet()) {
            orderedDish = findCookedDishInMenuByName(orderDetail.getKey(), menu);
            if (orderedDish == null) {
                StringJoiner newString = new StringJoiner(" ");
                newString.add("Sorry, unfortunately but we don't have");
                newString.add(orderDetail.getKey());
                newString.add("at the moment");
                System.out.println(newString);
            } else {
                int countPortions = availableCount(orderDetail.getKey());
                int countDish = calculateCount(countPortions, orderDetail.getValue().intValue(), orderDetail.getKey());
                DishInOrder dishInOrder = new DishInOrder(countDish, orderedDish.getDishDetails());
                orderedDishs.add(dishInOrder);
            }
        }
        if (orderedDishs.size() > 0) {
            return new Order(orderedDishs, LocalDate.now(), tip);
        }
        return null;
    }

    public void changeCount(String nameDish, int count) throws Exception {
        List<CookedDish> someDishInMenu = menuDAO.getCookedDishesByName(nameDish);
        for (CookedDish cDish : someDishInMenu) {
            if (cDish.getCurrentCount() - count >= 0) {
                menuDAO.setCount(cDish.getId(), cDish.getCurrentCount() - count);
                count = 0;
            } else {
                count = count - cDish.getCurrentCount();
                menuDAO.setCount(cDish.getId(), 0);
            }
            if (count == 0) {
                break;
            }
        }
    }

    private void useDishsFromCurrentMenuToOrder() throws Exception {
        if (order != null) {
            for (DishInOrder dishInOrder : order.getSelectedDishes()) {
                int count = dishInOrder.getCount();
                String nameDish = dishInOrder.getDish().getNameDish();
                changeCount(nameDish, count);
            }
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void getNewOrder() throws Exception {
        order = fillOrder(inputData.inputInt("Would you like to leave a tip?"));
        if (order != null) {
            int orderId = orderDAO.addOrder(order);
            useDishsFromCurrentMenuToOrder();
            report.printOrder(orderId, order);
        }
    }
}
