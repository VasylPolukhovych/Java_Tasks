package accounting;

import common.Identifier;
import menu.CookedDish;
import menu.Menu;
import order.Order;

import java.time.LocalDate;
import java.util.*;


public class BasicAccounting implements Accounting {

    private List<CookedDish> allCookedDishes = new ArrayList<>();
    private List<CookedDish> spoiledDishes = new ArrayList<>();
    private Map<Identifier, Order> orders = new HashMap<>();

    public BasicAccounting() {
    }

    @Override
    public List<CookedDish> getAllCookedDishes() {
        return Collections.unmodifiableList(allCookedDishes);
    }

    @Override
    public List<CookedDish> getSpoiledDishes() {
        return Collections.unmodifiableList(spoiledDishes);
    }

    @Override
    public Map<Identifier, Order> getOrders() {
        return orders;
    }


    @Override
    public Identifier saveCompleted(Order order) {
        Identifier orderKey = new Identifier();
        orders.put(orderKey, order);
        return orderKey;
    }

    @Override
    public void disposeOfOverdueDishes(Menu menu) {
        List<CookedDish> cookedDishesToRemoveFromMenu = new ArrayList<>();
        for (CookedDish cookedDish : menu.getCurrentMenu()) {
            if (cookedDish.isDishSpoiled(LocalDate.now())) {
                spoiledDishes.add(cookedDish);
                cookedDishesToRemoveFromMenu.add(cookedDish);
            }
        }
        menu.getCurrentMenu().removeAll(cookedDishesToRemoveFromMenu);
    }

    @Override
    public void fillAllCookedDishsByMenu(Menu menu) {
        for (CookedDish cookedDish : menu.getCurrentMenu()) {
            allCookedDishes.add(cookedDish);
        }
    }

}