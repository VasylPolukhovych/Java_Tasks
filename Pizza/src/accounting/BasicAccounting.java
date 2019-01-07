package accounting;

import common.Identifier;
import menu.CookedDish;
import menu.CookedDishes;
import menu.Menu;
import order.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class BasicAccounting implements Accounting {

    private CookedDishes allCookeddishes = new CookedDishes();
    private CookedDishes spoiledDishes = new CookedDishes();
    private Orders orders = new Orders();

    @Override
    public void addDishToList(CookedDish cookedDish, CookedDishes dishes) {
        dishes.getCookedDishes().add(cookedDish);
    }

    @Override
    public Identifier addOrderToOrders(Order order, Orders orders) {
        Identifier orderKey = new Identifier();
        orders.getOrders().put(orderKey, order);
        return orderKey;
    }

    @Override
    public void disposeOfOverdueDishes(CookedDishes menu) {
        List<CookedDish> cookedDishesToRemoveFromMenu = new LinkedList<>();
        for (CookedDish cookedDish : menu.getCookedDishes()) {
            if (!(menu.isExpirationDateExpired(cookedDish.getDateOfMaking(), cookedDish.getDish().getExpirationDate()))) {
                spoiledDishes.getCookedDishes().add(cookedDish);
                cookedDishesToRemoveFromMenu.add(cookedDish);
            }
        }
        menu.getCookedDishes().removeAll(cookedDishesToRemoveFromMenu);
    }

    @Override
    public CookedDishes getAllCookeddishes() {
        return allCookeddishes;
    }


    @Override
    public CookedDishes getSpoiledDishes() {
        return spoiledDishes;
    }

    @Override
    public Orders getOrders() {
        return orders;
    }


}