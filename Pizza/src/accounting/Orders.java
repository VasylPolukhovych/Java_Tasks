package accounting;

import common.Identifier;
import order.Order;
import java.util.HashMap;
import java.util.Map;

public class Orders {
    private Map<Identifier, Order> orders = new HashMap<>();

    public Orders() {
    }

    public Map<Identifier, Order> getOrders() {
        return orders;
    }
}
