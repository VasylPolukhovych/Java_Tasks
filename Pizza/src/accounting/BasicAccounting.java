package accounting;

import common.Identifier;
import menu.CookedDish;
import menu.Menu;
import order.Order;

import java.util.*;


public class BasicAccounting implements Accounting {

    private List<CookedDish> AllCookedDishes = new ArrayList<>();
    private List<CookedDish> spoiledDishes = new ArrayList<>();
    private Map<Identifier, Order> orders = new HashMap<>();

    public BasicAccounting() {
    }

    private CookedDish createCopyCookedDish(CookedDish cookedDish) {
        return new CookedDish(cookedDish.getDishDetails(), cookedDish.getCount(), cookedDish.getDateOfMaking());
    }

    @Override
    public List<CookedDish> getAllCookedDishes() {
        return AllCookedDishes;
    }

    @Override
    public List<CookedDish> getSpoiledDishes() {
        return spoiledDishes;
    }

    @Override
    public Map<Identifier, Order> getOrders() {
        return orders;
    }


    @Override
    public Identifier addOrderToOrders(Order order) {
        Identifier orderKey = new Identifier();
        orders.put(orderKey, order);
        return orderKey;
    }

    @Override
    public void disposeOfOverdueDishes(Menu menu) {
        List<CookedDish> cookedDishesToRemoveFromMenu = new ArrayList<>();
        for (CookedDish cookedDish : menu.getCurrentMenu()) {
            if (!(menu.isExpirationDateExpired(cookedDish.getExpirationDate(), cookedDish))) {
                spoiledDishes.add(createCopyCookedDish(cookedDish));
                cookedDishesToRemoveFromMenu.add(cookedDish);
            }
        }
        menu.getCurrentMenu().removeAll(cookedDishesToRemoveFromMenu);
    }

    @Override
    public void fillAllCookedDishsByMenu(Menu menu) {
        for (CookedDish cookedDish : menu.getCurrentMenu()) {
            AllCookedDishes.add(createCopyCookedDish(cookedDish));
        }
    }

}