package pizza.output;

import pizza.dto.CookedDish;
import pizza.dto.Order;
import pizza.exception.OrderNotfoundException;

import java.time.LocalDate;
import java.util.List;

public class Reports {

    public void printDishs(List<CookedDish> dishes, String reportName) {
        System.out.println("*****************************************");
        System.out.println(reportName);
        if (reportName.equals("All spoiled dishes")) {
            dishes.stream().filter(x -> x.getDateOfMaking().plusDays(x.getShelfLife().toDays()).compareTo(LocalDate.now()) < 0).
                    map(x -> "Dish - " + x.getNameDish() + ". " +
                            "Count - " + x.getCurrentCount() + ". " +
                            "Cost of cost -" + x.getCostOfCosts().getCount() + ". " +
                            "Price - " + x.getPrice().getCount() + "." +
                            "Date of making - " + x.getDateOfMaking() + ". " +
                            "Days - " + x.getShelfLife().toDays() + ". ").forEach(System.out::println);
        } else {
            for (CookedDish cookedDish : dishes) {
                System.out.printf("Dish - %s . Count - %d . Cost of cost - %10.2f . Price - %10.2f . Date of making  %s . Days -%d"
                        , cookedDish.getNameDish(), cookedDish.getCount(), cookedDish.getCostOfCosts().getCount(), cookedDish.getPrice().getCount(),
                        cookedDish.getDateOfMaking(), cookedDish.getShelfLife().toDays());
                System.out.println();
            }
        }
    }

    public void printMenu(List<CookedDish> currentMenu) {
        System.out.println("*****************************************");
        System.out.println("Menu");
        currentMenu.
                stream()
                .map(x -> "Dish - " + x.getNameDish() + ". " +
                        "Count - " + x.getCurrentCount() + ". " +
                        "Price - " + x.getPrice().getCount() + ".").forEach(System.out::println);
        System.out.println("*****************************************");
    }

    public void printOrder(int orderId, Order order) {
        if (order == null) {
            new OrderNotfoundException(orderId);
        } else {
            double summa = order.getSelectedDishes().stream().mapToDouble(i -> i.getCount() * i.getDish().getPrice().getCount()).sum();


            System.out.println("Bill #" + orderId);
            System.out.println("________________________________________________");
            order.getSelectedDishes().stream().map(i -> "Dish - " + i.getDish().getNameDish() +
                    " Count - " + i.getCount() +
                    " Price " + i.getDish().getPrice().getCount())
                    .forEach(System.out::println);
            System.out.println("");
            System.out.println("________________________________________________");
            System.out.printf("Summa = %8.2f Tip = %d Total = %8.2f", summa, order.getTip(), (summa + summa * order.getTip() / 100));
            System.out.println("");
        }
    }
}


