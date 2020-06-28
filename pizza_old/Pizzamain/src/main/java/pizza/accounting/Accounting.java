package pizza.accounting;

import pizza.common.Identifier;
import pizza.menu.CookedDish;
import pizza.menu.Menu;
import pizza.order.Order;

import java.util.List;
import java.util.Map;

public interface Accounting {

    List<CookedDish> getAllCookedDishes();

    List<CookedDish> getSpoiledDishes();

    Map<Identifier, Order> getOrders();

    Identifier saveCompleted(Order order);

    void disposeOfOverdueDishes(Menu menu);

    void fillAllCookedDishsByMenu(Menu menu);

    void addCookedDish(CookedDish cookedDish);
}

