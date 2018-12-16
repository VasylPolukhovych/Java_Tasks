package accounting;
import java.util.Arrays;
import order.*;
import menu.*;
public class Accounting implements IAccounting {

    public Orders addElement(Orders orders, Order element) {
        Order[] ordAr;
        ordAr = Arrays.copyOf(orders.getOrders(), orders.getOrders().length + 1);
        orders.setOrders(ordAr);
        orders.getOrders()[orders.getOrders().length - 1] = element;
        return orders;
    }

    public void minusDish(Menu menu, Order ord) {
        CookedDish cookedDishW = new CookedDish();

        for (int j = 0; j < ord.getArrayDishs().length; j++) {

            for (int i = 0; i < menu.getMenu().length; i++) {
                if (menu.getMenu()[i].getIdCookedDish() == ord.getArrayDishs()[j].getCookedDishId()) {
                    menu.getMenu()[i].setCount(menu.getMenu()[i].getCount() - ord.getArrayDishs()[j].getCount());
               }
            }
        }
    }
}
