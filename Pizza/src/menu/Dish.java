package menu;
import common.Identifier;
import common.Money;
import java.util.Objects;

public class Dish  implements Comparable<Dish> {
    private Identifier idDish = new Identifier();
    private String nameDish;
    private Money costOfCosts;
    private Money price;
    private Long expirationDate;

    public Dish() {
    }

    public String getNameDish() {
        return nameDish;
    }

    public void setNameDish(String nameDish) {
        this.nameDish = nameDish;
    }

    public Money getCostOfCosts() {
        return costOfCosts;
    }

    public void setCostOfCosts(double costOfCosts) {
        this.costOfCosts = new Money(costOfCosts);
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = new Money(price);

    }

    public Long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Identifier getIdDish() {
        return idDish;
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
