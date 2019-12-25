package pizza.menu;

import java.time.LocalDate;
import java.util.Objects;

public class CookedDish extends Dish {
    private int count;
    private LocalDate dateOfMaking;


    public CookedDish(Dish dish, int count, LocalDate dateOfMaking) {
        super(dish.getNameDish(), dish.getCostOfCosts(), dish.getPrice(), dish.getShelfLife());
        this.count = count;
        this.dateOfMaking = dateOfMaking;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public boolean canDishBeSold(String nameDish, CookedDish cookedDish) {
        boolean result = cookedDish.getNameDish().equalsIgnoreCase(nameDish);
        result = result && cookedDish.getCount() > 0;
        result = result && (!isDishSpoiled(LocalDate.now()));
        return result;
    }

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
