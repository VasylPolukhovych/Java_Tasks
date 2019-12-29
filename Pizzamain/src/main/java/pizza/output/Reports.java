package pizza.output;

import pizza.accounting.Accounting;
import pizza.common.Identifier;
import pizza.menu.CookedDish;
import pizza.menu.Menu;
import pizza.order.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Reports {

    public void printDishs(List<CookedDish> dishes, String reportName) {
        System.out.println("*****************************************");
        System.out.println(reportName);
        for (CookedDish cookedDish : dishes) {
            System.out.printf("Dish - %s . Count - %d . Cost of cost - %10.2f . Price - %10.2f . Date of making  %s . Days -%d"
                    , cookedDish.getNameDish(), cookedDish.getCount(), cookedDish.getCostOfCosts().getCount(), cookedDish.getPrice().getCount(), cookedDish.getDateOfMaking(), cookedDish.getShelfLife().toDays());
            System.out.println();
        }
    }

    public void printMenu(Menu currentMenu) {
        System.out.println("*****************************************");
        System.out.println("Menu");
        currentMenu.getCurrentMenu().stream()
                .filter(x -> !x.isDishSpoiled(LocalDate.now()))
                .map(x -> "Dish - " + x.getNameDish() + ". " +
                        "Count - " + x.getCurrentCount() + ". " +
                        "Price - " + x.getPrice().getCount() + ".").forEach(System.out::println);
        System.out.println("*****************************************");
    }

    public void printOrder(Identifier orderId, Accounting orders) {
        try {

            Order orderForPrint = orders.getOrders().entrySet().stream()
                    .filter(x -> orderId.equals(x.getKey()))
                    .map(x -> x.getValue()).findFirst()
                    .orElseThrow(() -> new Exception("Bill #" + orderId.getId() + " does not exist on the system"));
            double summa = orderForPrint.getSumma().getCount();
            int tip = orderForPrint.getTip();
            System.out.println("Bill #" + orderId.getId());
            System.out.println("________________________________________________");
            orderForPrint.getSelectedDishes().entrySet()
                    .stream().map(i -> "Dish - " + i.getValue().getDish().getNameDish() +
                    " Count - " + i.getValue().getCount() +
                    " Price " + i.getValue().getDish().getPrice().getCount())
                    .forEach(System.out::println);
            System.out.println("");
            System.out.println("________________________________________________");
            System.out.printf("Summa = %8.2f Tip = %d Total = %8.2f", summa, tip, (summa + summa * tip / 100));
            System.out.println("");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void salesRegister(List<CookedDish> dishes) {
        System.out.println("*****************************************");
        System.out.println("Sales Register");
        System.out.println("Summa - " +
                dishes.stream()
                        .filter( x -> x.getCurrentCount() != x.getCount())
                        .peek( x -> System.out.println(
                                "Dish - " + x.getNameDish() + ". " +
                                "Count - " + (x.getCount() - x.getCurrentCount()) + ". " +
                                "Price - " + x.getPrice().getCount() + "."))
                        .collect(Collectors.summingDouble(
                                x -> (x.getCount() - x.getCurrentCount()) * x.getDishDetails().getPrice().getCount())
                        )
        );
        System.out.println("*****************************************");
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
