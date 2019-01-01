package accounting;
import common.Identifier;
import menu.CookedDish;
import menu.CookedDishes;
import menu.Menu;
import order.Order;

public interface Accounting {

    void addDishToList(CookedDish cookedDish, CookedDishes dishes);

    Identifier addOrderToOrders(Order order, Orders orders);

    void disposeOfOverdueDishes (CookedDishes menu);

    CookedDishes getAllCookeddishes();

    CookedDishes getSpoiledDishes();

    Orders getOrders();
}

