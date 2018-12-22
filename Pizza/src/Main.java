
import java.time.LocalDate;
import java.util.*;

import common.ICommonMethods;
import order.*;
import menu.*;
import accounting.*;

public class Main {


    public static void main(String[] args) {

        // Initialization
        ActionsWithClient aC = new ActionsWithClient();
        Accounting accnt = new Accounting();
        ActionsWithMenu aM = new ActionsWithMenu();

        //Create Collections
        HashSet<Dish> dishs = new HashSet<>();
        LinkedList<CookedDish> menu = new LinkedList<>();
        HashMap<Integer, Order> orders = new HashMap<>();

        Random rd = new Random();

        //Add dishs
        for (int j = 0; j < 6; j++) {
            Dish dish = new Dish();
            dish.setIdDish(j + 1);
            dish.setCostOfCosts(ICommonMethods.toCurrency(rd.nextFloat() * 100));
            dish.setExpirationDate(j + rd.nextInt(355));
            dish.setPrice(ICommonMethods.toCurrency(dish.getCostOfCosts() + (dish.getCostOfCosts() * 30 / 100)));
            dish.setNameDish("Pizza " + rd.nextInt(10));
            dishs.add(dish);
        }

        //Cook Dish
        for (Dish dd : dishs) {
            CookedDish cD = new CookedDish();

            cD.setIdCookedDish(aM.getNextIdForMenu(menu));
            cD.setCount(rd.nextInt(100));
            cD.setDateOfMaking(LocalDate.of(2018, 12 /*rd.nextInt(11) + 18*/, rd.nextInt(27) + 1));
            cD.setDish(dd);
            menu.add(cD);
          if (dd.getIdDish() == 2) {
              //Add the same dish
              CookedDish cD1 = new CookedDish();
              cD1.setIdCookedDish(aM.getNextIdForMenu(menu));
              cD1.setCount(rd.nextInt(100));
              cD1.setDateOfMaking(LocalDate.of(2018, 12 /*rd.nextInt(11) + 18*/, rd.nextInt(27) + 1));
              cD1.setDish(dd);
              menu.add(cD1);
          }
        }

        //Print Menu
        aM.printMenu(menu);

        //Input order
        Order order = aC.inputOrder(menu);

        //Add new order to Array, change count of Dishes

        Integer orderKey = accnt.addOrderToOrders(order,orders);

        accnt.minusDish(menu, order);

        //Print bill
        aC.printOrder(orderKey,orders );

        //Print Menu
        aM.printMenu(menu);

    }


}