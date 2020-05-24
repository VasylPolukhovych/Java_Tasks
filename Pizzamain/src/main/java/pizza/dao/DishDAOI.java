package pizza.dao;

import pizza.dto.DishInOrder;

import java.util.List;

public interface DishDAOI {
    List<DishInOrder> getDishesInOrder(int idOrder) throws Exception;

}
