package pizza.exception;

public class OrderNotfoundException extends Exception {
private int orderId;

    public OrderNotfoundException(int orderId) {

        this.orderId = orderId;
        System.out.println("Bill #" + orderId + " does not exist on the system");
    }
}
