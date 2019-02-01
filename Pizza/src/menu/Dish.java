package menu;
import common.Money;
import java.util.Objects;

public class Dish  implements Comparable<Dish> {
    private String nameDish;
    private Money costOfCosts;
    private Money price;
    private int expirationDate;

    public Dish(String nameDish, Money costOfCosts, Money price, int expirationDate) {
        this.nameDish = nameDish;
        this.costOfCosts = costOfCosts;
        this.price = price;
        this.expirationDate = expirationDate;
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

    public int getExpirationDate() {
        return expirationDate;
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
