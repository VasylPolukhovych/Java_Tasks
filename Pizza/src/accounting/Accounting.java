package accounting;

import common.Identifier;
import menu.CookedDish;
import menu.Menu;
import order.Order;

import java.util.List;
import java.util.Map;

public interface Accounting {

    List<CookedDish> getAllCookedDishes();

    List<CookedDish> getSpoiledDishes();

    Map<Identifier, Order> getOrders();

    Identifier saveCompleted(Order order);

    void disposeOfOverdueDishes(Menu menu);

    void fillAllCookedDishsByMenu(Menu menu);
}

