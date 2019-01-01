package menu;

import accounting.Accounting;
import common.Identifier;
import order.DishInOrder;
import order.Order;
import java.time.LocalDate;
import java.util.*;


public class CurrentMenu implements Menu {

    private CookedDishes currentMenu = new CookedDishes();
    private Set<Dish> dishs = new HashSet<>();
    private Random rd = new Random();

    public CurrentMenu() {
    }

    @Override
    public CookedDishes getMenu() {
        return currentMenu;
    }

    @Override
    public Dish getDishByName(String name, Identifier id, Set<Dish> dishs) {
        for (Dish dish : dishs) {
            if (dish.getNameDish().equals(name) && dish.getIdDish() != id) {
                return dish;
            }
        }
        return null;
    }

    @Override
    public CookedDish findMenuItem(String nameDish, CookedDishes currentMenu) {
        for (CookedDish cookedDish : currentMenu.getCookedDishes()) {
            if (cookedDish.getDish().getNameDish().equalsIgnoreCase(nameDish)) {
                if (currentMenu.isExpirationDateExpired(cookedDish.getDateOfMaking(), cookedDish.getDish().getExpirationDate()) && cookedDish.getCount() > 0) {
                    return cookedDish;
                }
            }
        }
        return null;
    }

    @Override
    public void useDishsFromCurrentMenuToOrder(CookedDishes currentMenu, Order order) {
        if (order != null) {
            for (Map.Entry<String, DishInOrder> dishInOrder : order.getSelectedDishes().entrySet()) {
                Long count = dishInOrder.getValue().getCount();
                List<CookedDish> forDel = new ArrayList<>();
                for (CookedDish cookedDish : currentMenu.getCookedDishes()) {
                    if (dishInOrder.getKey().equalsIgnoreCase(cookedDish.getDish().getNameDish())) {
                        cookedDish.setCount(cookedDish.getCount() - count);
                        if (cookedDish.getCount() <= 0) {
                            count = cookedDish.getCount() * (-1);
                            forDel.add(cookedDish);
                        }
                    }
                }
                currentMenu.getCookedDishes().removeAll(forDel);
            }
        }
    }

    @Override
    public void addDishs(int countOfDishs) {
        for (int j = 0; j < 6; j++) {
            Dish dish = new Dish();
            Long expirationDate = (long) (j + rd.nextInt(31));
            dish.setExpirationDate(expirationDate);
            dish.setCostOfCosts((rd.nextInt(10000) / 100));
            dish.setPrice(dish.getCostOfCosts().add(dish.getCostOfCosts().multiply(30).divide(100)).getCount());
            dish.setNameDish("Pizza " + rd.nextInt(10));
            dishs.add(dish);
        }
    }

    @Override
    public void cookDishs(int countOfDishs, Accounting accounting) {
        for (Dish dd : dishs) {
            CookedDish cD = new CookedDish();
            Long count = (long) rd.nextInt(100);
            cD.setCount(count);
            cD.setDateOfMaking(LocalDate.of(2018, 12, rd.nextInt(27) + 1));
            cD.setDish(dd);
            currentMenu.getCookedDishes().add(cD);
            if (dd.getIdDish().getId() == 2) {
                CookedDish cD1 = new CookedDish();
                cD1.setCount((long) rd.nextInt(100));
                cD1.setDateOfMaking(LocalDate.of(2018, 12, rd.nextInt(27) + 1));
                cD1.setDish(dd);
                currentMenu.getCookedDishes().add(cD1);

            }
        }
        accounting.getAllCookeddishes().setCookedDishes(currentMenu.getCookedDishes());
    }

}