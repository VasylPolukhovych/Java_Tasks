package pizza.dto;

public class DishInOrder {
    private Dish dish;
    private int count;
    private int orderId;
    private String message;

    public DishInOrder(Dish dish, int count, String message, int orderId) {
        this.dish = dish;
        this.count = count;
        this.orderId = orderId;
    }

    public DishInOrder(Dish dish, int count) {
        this.dish = dish;
        this.count = count;
    }

    public DishInOrder() {
    }

    public int getCount() {
        return count;
    }

    public Dish getDish() {
        return dish;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOrderId() {
        return orderId;
    }


}


