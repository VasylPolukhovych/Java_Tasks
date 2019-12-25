package pizza.accounting;

import pizza.common.Identifier;
import pizza.menu.CookedDish;
import pizza.menu.Menu;
import pizza.order.Order;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class BasicAccounting implements Accounting {

    private List<CookedDish> allCookedDishes = new ArrayList();
    private List<CookedDish> spoiledDishes = new ArrayList();
    private Map<Identifier, Order> orders = new HashMap();

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
        List<CookedDish> cookedDishesToRemoveFromMenu = new ArrayList();
        cookedDishesToRemoveFromMenu = menu.getCurrentMenu().stream()
                .filter(x -> x.isDishSpoiled(LocalDate.now()))
                .collect(Collectors.toList());
        spoiledDishes.addAll(cookedDishesToRemoveFromMenu);
        menu.removeDishsFromMenu(cookedDishesToRemoveFromMenu);
    }

    @Override
    public void fillAllCookedDishsByMenu(Menu menu) {
        allCookedDishes.addAll(menu.getCurrentMenu());
    }

}