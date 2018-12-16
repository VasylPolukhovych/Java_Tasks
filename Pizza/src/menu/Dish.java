package menu;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Formatter;


public class Dish {
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
}
