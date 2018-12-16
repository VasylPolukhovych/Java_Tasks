
import java.time.LocalDate;
import java.util.Random;
import order.*;
import menu.*;
import accounting.*;
public class Main {


    public static void main(String[] args) {

        // Initialization
        ActionsClient aC = new ActionsClient();
        Accounting accnt = new Accounting();
        //Add dishs
        Dish[] dishs = new Dish[5];
        Random rd = new Random();
        for (int j = 0; j < (dishs.length); j++) {
            Dish dish = new Dish();
            dish.setIdDish(j + 1);
            dish.setCostOfCosts(IActionsClient.toCurrency(rd.nextFloat() * 100));
            dish.setExpirationDate(j + rd.nextInt(355));
            dish.setPrice(IActionsClient.toCurrency(dish.getCostOfCosts() + (dish.getCostOfCosts() * 30 / 100)));

            dish.setNameDish("Pizza " + rd.nextInt(100));
            dishs[j] = dish;
        }


        //Cooking Dish
        CookedDish[] cookedDishes = new CookedDish[4];
        for (int j = 0; j < (cookedDishes.length); j++) {

            CookedDish cD = new CookedDish();
            cD.setIdCookedDish(j);
            cD.setCount(rd.nextInt(100));
            cD.setDateOfMaking(LocalDate.of(2018, 12 /*rd.nextInt(11) + 18*/, rd.nextInt(27) + 1));
            cD.setDish(dishs[j]);
            cookedDishes[j] = cD;
        }
        //Create Menu
        Menu menu = new Menu();
        menu.setMenu(cookedDishes);
        //Print Menu
        aC.printMenu(menu);

        //Create order
        Order order = new Order();

        //Create array of Orders
        Orders arrayOfOrders = new Orders();
        arrayOfOrders.setOrders(new Order[1]);

        // Input Order
        order = aC.inputOrder(menu);
        //add new order to Array, change count of Dishes,print order
        if (order != null) {
            if (arrayOfOrders.getOrders().length > 1) {
                accnt.addElement(arrayOfOrders, order);
            }
            arrayOfOrders.getOrders()[arrayOfOrders.getOrders().length - 1] = order;
            arrayOfOrders.getOrders()[arrayOfOrders.getOrders().length - 1].setIdOrder(arrayOfOrders.getOrders().length);

            accnt.minusDish(menu, order);

            //Print bill
            aC.printOrder(order.getIdOrder(), arrayOfOrders, menu);
        }

    }

}