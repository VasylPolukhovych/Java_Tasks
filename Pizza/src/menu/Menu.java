package menu;

import accounting.Accounting;
import common.Identifier;
import order.Order;
import java.util.Set;

public interface Menu {

    void addDishs(int countOfDishs);

    void cookDishs(int countOfDishs, Accounting accounting);

    Dish getDishByName(String name, Identifier id, Set<Dish> dishs);

    CookedDish findMenuItem(String nameDish, CookedDishes currentMenu);

    void useDishsFromCurrentMenuToOrder(CookedDishes currentMenu, Order ord);

    CookedDishes getMenu();
}

