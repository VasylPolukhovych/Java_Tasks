package pizza.dao;

import pizza.dto.DishInOrder;
import pizza.dto.Order;
import java.util.List;

public interface OrderDAOI {
    int addOrder(Order order) throws Exception;
    Order getOrder(int id, List<DishInOrder> dishInOrder) throws Exception;
}
