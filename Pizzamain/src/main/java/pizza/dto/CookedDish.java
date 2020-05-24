package pizza.dto;

import java.time.LocalDate;
import java.util.Objects;

public class CookedDish extends Dish {
    private int id;
    private int count;
    private int currentCount;
    private LocalDate dateOfMaking;


    public CookedDish(int id, Dish dish, int count, int currentCount, LocalDate dateOfMaking) {
        super(dish.getNameDish(), dish.getCostOfCosts(), dish.getPrice(), dish.getShelfLife());
        this.count = count;
        this.currentCount = currentCount;
        this.dateOfMaking = dateOfMaking;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public int getCount() {
        return count;
    }

    public LocalDate getDateOfMaking() {
        return dateOfMaking;
    }

    public Dish getDishDetails() {
        return new Dish(this.getNameDish(), this.getCostOfCosts(), this.getPrice(), this.getShelfLife());
    }

    public boolean isDishSpoiled(LocalDate date) {

        if (dateOfMaking.plusDays(getShelfLife().toDays()).compareTo(date) > 0) {
            return false;
        }
        return true;
    }

    /*public boolean canDishBeSold(String nameDish, CookedDish cookedDish) {
        boolean result = cookedDish.getNameDish().equalsIgnoreCase(nameDish);
        result = result && cookedDish.getCount() > 0;
        result = result && (!isDishSpoiled(LocalDate.now()));
        return result;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CookedDish cookedDish = (CookedDish) o;
        return getNameDish().equals(cookedDish.getNameDish()) && (Objects.equals(dateOfMaking, cookedDish.dateOfMaking)) && count == cookedDish.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNameDish(), dateOfMaking, count);
    }

}
