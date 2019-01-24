package menu;

import accounting.Accounting;
import common.Money;
import order.DishInOrder;
import order.Order;
import output.Reports;
import java.time.LocalDate;
import java.util.*;


public class CurrentMenu implements Menu {
    private Reports report = new Reports();
    private Set<Dish> dishs = new HashSet<>();
    private List<CookedDish> currentMenu = new LinkedList<>();

    private Random rd = new Random();

    public CurrentMenu() {
    }

    private Dish createRandomDish(int number) {
        String dishName = "Pizza " + rd.nextInt(10);
        int expirationDate = number + rd.nextInt(31);
        Money costOfCosts = new Money((double) (rd.nextInt(10000) / 100));
        Money price = new Money(costOfCosts.add(costOfCosts.multiply(30).divide(100)).getCount());
        return new Dish(dishName, costOfCosts, price, expirationDate);
    }

    private CookedDish createRandomCookedDish(Dish dish) {
        int count = rd.nextInt(100);
        LocalDate dateOfMaking = LocalDate.of(2018, 12, rd.nextInt(27) + 1);
        return new CookedDish(dish, count, dateOfMaking);

    }

    private boolean canDishBeSold(String nameDish,CookedDish cookedDish) {
        boolean result = cookedDish.getNameDish().equalsIgnoreCase(nameDish);
        result = result && cookedDish.getCount() > 0;
        result = result && isExpirationDateExpired(cookedDish.getExpirationDate(), cookedDish);
        return result;
    }

    private void changeCurrentMenuByOrder (String nameDish,int count) {
        List<CookedDish> forDeleting = new ArrayList<>();
        List<CookedDish> forAdding = new ArrayList<>();
        for (CookedDish cookedDish : currentMenu) {
            if (nameDish.equalsIgnoreCase(cookedDish.getNameDish())) {
                int newCount = cookedDish.getCount() - count;
                if (newCount <= 0) {
                    count = newCount * (-1);
                    forDeleting.add(cookedDish);
                } else {
                    forAdding.add(new CookedDish(cookedDish.getDishDetails(), newCount, cookedDish.getDateOfMaking()));
                    forDeleting.add(cookedDish);
                    break;
                }
            }
        }
        currentMenu.removeAll(forDeleting);
        currentMenu.addAll(forAdding);
    }

    @Override
    public List<CookedDish> getCurrentMenu() {
        return currentMenu;
    }

    @Override
    public boolean isExpirationDateExpired(int expirationDate, CookedDish cookedDish) {
        LocalDate currentDate = LocalDate.now();
        if (cookedDish.getDateOfMaking().plusDays(expirationDate).compareTo(currentDate) > 0) {
            return false;
        }
        return true;
    }

    @Override
    public CookedDish findCookedDishInMenuByName(String nameDish) {
        for (CookedDish cookedDish : currentMenu) {
            if (canDishBeSold(nameDish, cookedDish)) {
                return cookedDish;
            }
        }
        return null;
    }

    @Override
    public void useDishsFromCurrentMenuToOrder(Order order) {
        if (order != null) {
            for (Map.Entry<String, DishInOrder> dishInOrder : order.getSelectedDishes().entrySet()) {
                int count = dishInOrder.getValue().getCount();
                String nameDish = dishInOrder.getKey();
                changeCurrentMenuByOrder(nameDish, count);
            }
        }
    }

    @Override
    public void addDishs(int countOfDishs) {
        for (int j = 0; j < 6; j++) {
            dishs.add(createRandomDish(j));
        }
    }

    @Override
    public void cookDishs(int countOfDishs, Accounting accounting) {
        int i = 0;
        for (Dish dish : dishs) {
            i++;
            currentMenu.add(createRandomCookedDish(dish));
            if (i == 2) {
                currentMenu.add(createRandomCookedDish(dish));
            }
        }
    }

    @Override
    public int availableCountOfPortionsDishInMenu(String dishName, int neededCount) {
        int totalCount = 0;
        for (CookedDish cookedDish : currentMenu) {
            if (cookedDish.getNameDish().equalsIgnoreCase(dishName)) {
                totalCount = totalCount + cookedDish.getCount();
                if (totalCount >= neededCount) {
                    return neededCount;
                }
            }
        }
        List<String> strings = new ArrayList<>();
        strings.add("Sorry, unfortunately but we have only ");
        strings.add(((Integer) totalCount).toString());
        strings.add(dishName);
        strings.add(" at the moment");
        report.printMesage(strings);
        return totalCount;
    }

}