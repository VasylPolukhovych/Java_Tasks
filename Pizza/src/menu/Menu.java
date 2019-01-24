package menu;

import accounting.Accounting;
import common.Identifier;
import order.Order;

import java.util.List;


public interface Menu {

    List<CookedDish> getCurrentMenu();
    //Dish getDishByName(String name, Identifier id);
    CookedDish findCookedDishInMenuByName(String nameDish);
    int availableCountOfPortionsDishInMenu(String dishName, int neededCount);
    boolean isExpirationDateExpired (int expirationDate,CookedDish cookedDish);
    void useDishsFromCurrentMenuToOrder( Order order);
    void addDishs(int countOfDishs);
    void cookDishs(int countOfDishs, Accounting accounting);
}

