package order;
import java.time.LocalDate;

public class Order {
    int idOrder;
    int tip;
    float price;
    LocalDate orderDate;
    DishForOrder[] arrayDishs;

    public Order() {
    }

    public Order(DishForOrder[] arrayDishs) {
        this.arrayDishs = arrayDishs;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public DishForOrder[] getArrayDishs() {
        return arrayDishs;
    }

    public void setArrayDishs(DishForOrder[] arrayDishs) {
        this.arrayDishs = arrayDishs;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


}
