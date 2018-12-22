package accounting;
import order.*;
import menu.*;

import java.util.HashMap;
import java.util.LinkedList;

public interface IAccounting {

    void minusDish(LinkedList<CookedDish> menu, Order ord);
    Integer addOrderToOrders (Order ord, HashMap<Integer,Order> orders);
}
