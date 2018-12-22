package menu;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class Dish  implements Comparable<Dish>{
    private int idDish;
    private String nameDish;
    private float costOfCosts;
    private float price;
    private int   expirationDate;

    public Dish() {
    }

    public String getNameDish() {
        return nameDish;
    }

    public void setNameDish(String nameDish) {
        this.nameDish = nameDish;
    }

    public float getCostOfCosts() {
        return costOfCosts;
    }

    public void setCostOfCosts(float costOfCosts) {
        this.costOfCosts = costOfCosts;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(int expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getIdDish() {
        return idDish;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    @Override
    public int compareTo(@NotNull Dish o) {
        return nameDish.compareTo(o.getNameDish() );

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
