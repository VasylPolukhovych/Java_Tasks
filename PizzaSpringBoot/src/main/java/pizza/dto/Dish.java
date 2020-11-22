package pizza.dto;

import pizza.dto.common.Money;

import java.time.Duration;
import java.util.Objects;

public class Dish implements Comparable<Dish> {
    private String nameDish;
    private Money costOfCosts;
    private Money price;
    private Duration shelfLife;

    public Dish(String nameDish) {
        this.nameDish = nameDish;
    }

    public Dish(String nameDish, Money costOfCosts, Money price, Duration shelfLife) {
        this.nameDish = nameDish;
        this.costOfCosts = costOfCosts;
        this.price = price;
        this.shelfLife = shelfLife;
    }

    public Dish() {
    }

    public String getNameDish() {
        return nameDish;
    }

    public Money getCostOfCosts() {
        return costOfCosts;
    }

    public Money getPrice() {
        return price;
    }

    public Duration getShelfLife() {
        return shelfLife;
    }

    @Override
    public int compareTo(Dish o) {
        return nameDish.compareTo(o.getNameDish());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(nameDish, dish.nameDish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameDish);
    }
}
