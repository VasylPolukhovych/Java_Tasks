package order;
import java.time.LocalDate;
import java.util.*;

import accounting.Accounting;
import common.Identifier;
import input.InputData;
import input.InputDataOfOrder;
import menu.CookedDish;
import menu.Menu;
import output.Reports;


public class MainActionsWithClient implements ActionsWithClient {
    private Reports report = new Reports();
    private InputData inputDataOfOrder = new InputDataOfOrder();
    private Order order;

    public MainActionsWithClient() {
    }

    private Order fillOrder(Map<String, Long> orderDetails, int tip, Menu menu) {
        CookedDish orderedDish;
        HashMap<String, DishInOrder> orderedDishs = new HashMap<>();
        int cnt = 0;
        for (Map.Entry<String, Long> orderDetail : orderDetails.entrySet()) {
            orderedDish = menu.findCookedDishInMenuByName((orderDetail.getKey()));
            if (orderedDish == null) {
                List<String> strings = new ArrayList<>();
                strings.add("Sorry, unfortunately but we don't have ");
                strings.add(orderDetail.getKey());
                strings.add(" at the moment");
                report.printMesage(strings);
            } else {
                int countDishInOrder = menu.availableCountOfPortionsDishInMenu(orderDetail.getKey(), orderDetail.getValue().intValue());
                DishInOrder dishInOrder = new DishInOrder(countDishInOrder, orderedDish.getDishDetails());
                cnt++;
                orderedDishs.put(orderDetail.getKey(), dishInOrder);
            }
        }
        if (cnt > 0) {
            return new Order(orderedDishs, LocalDate.now(), tip);
        }
        return null;
    }

    @Override
    public void serveClient(Menu menu, InputData inputData, Accounting accounting) {

        order = fillOrder(inputDataOfOrder.inputDetails(), inputDataOfOrder.inputInt("Would you like to leave a tip?"), menu);
        Identifier orderKey = accounting.addOrderToOrders(order);
        menu.useDishsFromCurrentMenuToOrder(order);
        report.printOrder(orderKey, accounting);
    }

}
