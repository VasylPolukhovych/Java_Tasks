package order;
import java.time.LocalDate;
import java.util.*;

import accounting.Accounting;
import common.Identifier;
import common.Money;
import input.InputData;
import input.InputDataOfOrder;
import menu.CookedDish;
import menu.Menu;
import output.Reports;


public class MainActionsWithClient implements ActionsWithClient {
    private Reports report = new Reports();
    private InputData inputDataOfOrder = new InputDataOfOrder();
    private Order order =new Order();

    public MainActionsWithClient() {
    }


    private Money calcOrderSum(Map<String, DishInOrder> selectedDishes) {
        Money summa = new Money(0.00);
        for (Map.Entry<String, DishInOrder> item : selectedDishes.entrySet()) {
            summa = summa.add(item.getValue().getDish().getPrice().multiply(item.getValue().getCount()));
        }
        return summa;
    }

    private Long countDishsInMenu(String dishName, Long neededCount, List<CookedDish> currentMenu) {
        Long totalCount = new Long(0);
        for (CookedDish cookedDish : currentMenu) {
            if (cookedDish.getDish().getNameDish().equalsIgnoreCase(dishName)) {
                totalCount = totalCount + cookedDish.getCount();
                if (totalCount >= neededCount) {
                    return neededCount;
                }
            }
        }
        List<String> strings = new ArrayList<>();
        strings.add("Sorry, unfortunately but we have only ");
        strings.add(totalCount.toString());
        strings.add(dishName);
        strings.add(" at the moment");
        report.printMesage(strings);
        return totalCount;
    }

    @Override
    public Order fillOrder(Map<String, Long> orderDetails, Long tip,Menu menu) {
        Order newOrder = new Order();
        CookedDish orderedDish;
        HashMap<String, DishInOrder> orderedDishs = new HashMap<>();
        int cnt = 0;
        for (Map.Entry<String, Long> orderDetail : orderDetails.entrySet()) {
            orderedDish = menu.findMenuItem((orderDetail.getKey()), menu.getMenu());
            if (orderedDish == null) {
                List<String> strings = new ArrayList<>();
                strings.add("Sorry, unfortunately but we don't have ");
                strings.add(orderDetail.getKey() );
                strings.add(" at the moment");
                report.printMesage(strings);
            } else {
                DishInOrder dishInOrder = new DishInOrder();
                dishInOrder.setDish(orderedDish.getDish());
                dishInOrder.setCount(countDishsInMenu(orderDetail.getKey(), orderDetail.getValue().longValue(),
                        menu.getMenu().getCookedDishes()));
                cnt++;
                orderedDishs.put(orderDetail.getKey(), dishInOrder);
            }
        }
        if (cnt > 0) {
            newOrder.setSelectedDishes(orderedDishs);
            newOrder.setTip(tip);
            newOrder.setDate(LocalDate.now());
            newOrder.setSumma(calcOrderSum(orderedDishs).getCount());
            return newOrder;
        }
        return null;

    }

    @Override
    public void serveClient(Menu menu,InputData inputData,Accounting accounting) {

        order = fillOrder(inputDataOfOrder.inputDetails(), inputDataOfOrder.inputLong(), menu);

        Identifier orderKey = accounting.addOrderToOrders(order, accounting.getOrders());

        menu.useDishsFromCurrentMenuToOrder(menu.getMenu(), order);
        report.printOrder(orderKey, accounting.getOrders());
    }

}
