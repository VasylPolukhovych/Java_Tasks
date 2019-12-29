package pizza.menu;

import pizza.accounting.Accounting;
import pizza.common.Money;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


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
        LocalDate dateOfMaking = LocalDate.of(2019, 12, rd.nextInt(27) + 1);
        return new CookedDish(dish, count, dateOfMaking);

    }

    @Override
    public void changeCount(String nameDish, int count) {

        while (count > 0) {
            Optional<CookedDish> foundFirstDish = currentMenu.stream()
                    .filter(x -> nameDish.equalsIgnoreCase(x.getNameDish())
                            && x.getCurrentCount() > 0
                            && !(x.isDishSpoiled(LocalDate.now())))
                    .findFirst();

            if (foundFirstDish.isPresent()) {
                if (foundFirstDish.get().getCurrentCount() - count >= 0) {
                    foundFirstDish.get().setCurrentCount(foundFirstDish.get().getCurrentCount() - count);
                    count = 0;

                } else {
                    count = count - foundFirstDish.get().getCurrentCount();
                    foundFirstDish.get().setCurrentCount(0);
                }
            } else {
                count = 0;
            }
        }
        currentMenu.removeAll(currentMenu.stream().filter(x -> x.getCurrentCount() == 0).collect(Collectors.toList()));
    }

    @Override
    public List<CookedDish> getCurrentMenu() {
        return Collections.unmodifiableList(currentMenu);
    }

    @Override
    public CookedDish findCookedDishInMenuByName(String nameDish) {

        return currentMenu.stream().filter(x -> x.getNameDish().equalsIgnoreCase(nameDish)
                && x.getCurrentCount() > 0 && (!x.isDishSpoiled(LocalDate.now()))).findFirst().orElse(null);

    }

    @Override
    public void addDishs(int countOfDishs) {
        for (int j = 0; j < 5; j++) {
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
        return currentMenu.stream().filter(x -> x.getNameDish().equalsIgnoreCase(dishName)).
                collect(Collectors.summingInt(p -> p.getCurrentCount()));

    }

    @Override
    public void removeDishsFromMenu(List<CookedDish> cookedDishesToRemoveFromMenu) {
        currentMenu.removeAll(cookedDishesToRemoveFromMenu);

    }

    @Override
    public void addCookedDishToMenu(CookedDish cookedDish) {
        currentMenu.add(cookedDish);

    }

    ;

}