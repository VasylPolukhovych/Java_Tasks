package menu;

import common.Money;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;


public class CurrentMenu implements Menu {
    private Set<Dish> dishs = new HashSet<>();
    private List<CookedDish> currentMenu = new LinkedList<>();

    private Random rd = new Random();

    public CurrentMenu() {
    }

    private Dish createRandomDish(int number) {
        String dishName = "Pizza " + rd.nextInt(10);
        Duration shelfLife = Duration.ofDays(number + rd.nextInt(31));
        Money costOfCosts = new Money((double) (rd.nextInt(10000) / 100));
        Money price = new Money(costOfCosts.add(costOfCosts.multiply(30).divide(100)).getCount());
        return new Dish(dishName, costOfCosts, price, shelfLife);
    }

    private CookedDish createRandomCookedDish(Dish dish) {
        int count = rd.nextInt(100);
        LocalDate dateOfMaking = LocalDate.of(2019, 2, rd.nextInt(27) + 1);
        return new CookedDish(dish, count, dateOfMaking);

    }


    @Override
    public void changeCount(String nameDish, int count) {
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
        return Collections.unmodifiableList(currentMenu);
    }

    @Override
    public CookedDish findCookedDishInMenuByName(String nameDish) {
        for (CookedDish cookedDish : currentMenu) {
            if (cookedDish.canDishBeSold(nameDish, cookedDish)) {
                return cookedDish;
            }
        }
        return null;
    }


    @Override
    public void addDishs(int countOfDishs) {
        for (int j = 0; j < 3; j++) {
            dishs.add(createRandomDish(j));
        }
    }

    @Override
    public void cookDishs(int countOfDishs) {
        for (Dish dish : dishs) {
            currentMenu.add(createRandomCookedDish(dish));
            if (currentMenu.size() == 2) {
                currentMenu.add(createRandomCookedDish(dish));
            }
        }
    }

    @Override
    public int availableCount(String dishName) {
        int totalCount = 0;
        for (CookedDish cookedDish : currentMenu) {
            if (cookedDish.getNameDish().equalsIgnoreCase(dishName)) {
                totalCount = totalCount + cookedDish.getCount();
            }
        }
        return totalCount;
    }

    @Override
    public void removeDishsFromMenu(List<CookedDish> cookedDishesToRemoveFromMenu) {
        currentMenu.removeAll(cookedDishesToRemoveFromMenu);

    }

}