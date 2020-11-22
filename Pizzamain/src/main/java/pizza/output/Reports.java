package pizza.output;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pizza.annotation.CheckUserAnnotation;
import pizza.dao.CurrentMenuDAO;
import pizza.dto.CookedDish;
import pizza.dto.Order;
import pizza.exception.OrderNotfoundException;
import pizza.service.CheckUser;

import java.time.LocalDate;
import java.util.List;

public class Reports {
    @CheckUserAnnotation
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)

    public void printDishs(CheckUser checkUser, CurrentMenuDAO menuDAO, String reportName) throws Exception {
        List<CookedDish> dishes = menuDAO.getDishes();
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

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public void printMenu(CurrentMenuDAO menuDAO) throws Exception {
        List<CookedDish> currentMenu = menuDAO.getCurrentMenu();
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


