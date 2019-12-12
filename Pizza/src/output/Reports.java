package output;

import accounting.Accounting;
import common.Identifier;
import menu.CookedDish;
import menu.Menu;
import order.DishInOrder;
import order.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Reports {

    public void printDishs(List<CookedDish> dishes, String reportName) {
        System.out.println("*****************************************");
        System.out.println(reportName);
        for (CookedDish cookedDish : dishes) {
            System.out.printf("Dish - %s . Count - %d . Cost of cost - %10.2f . Price - %10.2f . Date of making  %s", cookedDish.getNameDish(), cookedDish.getCount(), cookedDish.getCostOfCosts().getCount(), cookedDish.getPrice().getCount(), cookedDish.getDateOfMaking());
            System.out.println();
        }
    }

    public void printMenu(Menu currentMenu) {
        System.out.println("*****************************************");
        System.out.println("Menu");
        for (CookedDish cookedDish : currentMenu.getCurrentMenu()) {
            if (!cookedDish.isDishSpoiled(LocalDate.now())) {
                System.out.printf("Dish - %s . Count - %d . Price - %10.2f", cookedDish.getNameDish(), cookedDish.getCount(), cookedDish.getPrice().getCount());
                System.out.println();
            }
        }
        System.out.println("*****************************************");
    }

    public void printOrder(Identifier orderId, Accounting orders) {
        Order orderForPrint = orders.getOrders().get(orderId);

        if (orderForPrint == null) {
            System.out.println("Bill #" + orderId.getId() + " does not exist on the system");
        } else {
            double summa = orderForPrint.getSumma().getCount();
            int tip = orderForPrint.getTip();
            System.out.println("Bill #" + orderId.getId());
            for (Map.Entry<String, DishInOrder> i : orderForPrint.getSelectedDishes().entrySet()) {
                System.out.printf("Dish - %s Count %d  Price %8.2f", i.getValue().getDish().getNameDish(), i.getValue().getCount(), i.getValue().getDish().getPrice().getCount());
                System.out.println("");
            }
            System.out.println("________________________________________________");
            System.out.printf("Summa = %8.2f Tip = %d Total = %8.2f", summa, tip, (summa + summa * tip / 100));
            System.out.println("");
        }
    }

    public void printMesage(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : strings) {
            stringBuilder.append(item);
        }
        String result = stringBuilder.toString();
        System.out.println(result);
    }

}
