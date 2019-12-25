package pizza.order;

import java.time.LocalDate;
import java.util.*;

import pizza.accounting.Accounting;
import pizza.common.Identifier;
import pizza.exception.OrderNotfoundException;
import pizza.input.InputData;
import pizza.input.InputDataOfOrder;
import pizza.menu.CookedDish;
import pizza.menu.Menu;
import pizza.output.Reports;


public class MainActionsWithClient implements ActionsWithClient {
    private Reports report = new Reports();
    private InputData inputDataOfOrder = new InputDataOfOrder();
    private Order order;

    public MainActionsWithClient() {
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


    private void useDishsFromCurrentMenuToOrder(Menu menu) {
        if (order != null) {
            for (Map.Entry<String, DishInOrder> dishInOrder : order.getSelectedDishes().entrySet()) {
                int count = dishInOrder.getValue().getCount();
                String nameDish = dishInOrder.getKey();
                menu.changeCount(nameDish, count);
            }
        }
    }

    private Order fillOrder(Map<String, Long> orderDetails, int tip, Menu menu) {
        CookedDish orderedDish;
        HashMap<String, DishInOrder> orderedDishs = new HashMap<>();
        for (Map.Entry<String, Long> orderDetail : orderDetails.entrySet()) {
            orderedDish = menu.findCookedDishInMenuByName((orderDetail.getKey()));
            if (orderedDish == null) {
                StringJoiner newString = new StringJoiner(" ");
                newString.add("Sorry, unfortunately but we don't have");
                newString.add(orderDetail.getKey());
                newString.add("at the moment");
                System.out.println(newString);
            } else {
                int countPortions = menu.availableCount(orderDetail.getKey());
                int countDish = calculateCount(countPortions, orderDetail.getValue().intValue(), orderDetail.getKey());
                DishInOrder dishInOrder = new DishInOrder(countDish, orderedDish.getDishDetails());
                orderedDishs.put(orderDetail.getKey(), dishInOrder);
            }
        }
        if (orderedDishs.size() > 0) {
            return new Order(orderedDishs, LocalDate.now(), tip);
        }
        return null;
    }

    @Override
    public void serveClient(Menu menu, InputData inputData, Accounting accounting)  {
        order = fillOrder(inputDataOfOrder.inputDetails(), inputDataOfOrder.inputInt("Would you like to leave a tip?"), menu);
        Identifier orderKey = accounting.saveCompleted(order);
        useDishsFromCurrentMenuToOrder(menu);

        report.printOrder(orderKey, accounting);

    }

}
