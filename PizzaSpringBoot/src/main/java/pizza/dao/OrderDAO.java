package pizza.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pizza.dto.DishInOrder;
import pizza.dto.Order;
import pizza.dto.mappers.DishInOrderMapper;
import pizza.dto.mappers.OrderMapper;

import java.util.List;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
@Repository

public class OrderDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String SQL_ADD_ORDER = "INSERT INTO public.order " +
            "(date, tip) " +
            "VALUES (current_date, ?)";
    private final String SQL_ADD_DISHES_IN_ORDER = "INSERT INTO public.dish_in_oreder( " +
            "name_dish, id_oreder, count, message) " +
            "VALUES (?, ?, ?, ?)";
    private final String SQL_GET_DISHES_IN__ORDER = "SELECT " +
            "d.name, d.cost_of_cost, d.price, d.shelf_life, " +
            "dor.count, dor.message , dor.id_oreder as order_id from public.dish d " +
            "join public.dish_in_oreder dor on d.name=dor.name_dish where dor.id_oreder = ?";
    private final String SQL_ORDER_BY_ID =
            "SELECT extract(day from o.date) dd, extract(month from o.date) mm, " +
                    "extract(year from o.date) yy, o.tip, o.id  FROM public.order o WHERE o.id = ? and 1 = 1";

    private final String SQL_GET_ORDER_ID = "SELECT max(id) id_order FROM public.order";

    public List<DishInOrder> getDishesInOrder(int idOrder) throws Exception {
        return jdbcTemplate.query(SQL_GET_DISHES_IN__ORDER, new Object[]{idOrder}, new DishInOrderMapper());
    }

    public List<DishInOrder> getOrderById(int idOrder) throws Exception {
        return jdbcTemplate.query(SQL_ORDER_BY_ID, new Object[]{idOrder}, new DishInOrderMapper());
    }

    public int addOrder(Order order) throws Exception {

        jdbcTemplate.update(SQL_ADD_ORDER, order.getTip());
        int orderId = jdbcTemplate.queryForObject(SQL_GET_ORDER_ID, Integer.class);
        for (DishInOrder dio : order.getSelectedDishes()
                ) {
            jdbcTemplate.update(SQL_ADD_DISHES_IN_ORDER, dio.getDish().getNameDish(),
                    orderId, dio.getCount(), dio.getMessage());
        }
        return orderId;

    }

    public Order getOrderDetailsById(int id) throws Exception {
        List<DishInOrder> dishesInOrder = getDishesInOrder(id);
        Order newOrder = jdbcTemplate.queryForObject(SQL_ORDER_BY_ID,
                new OrderMapper(dishesInOrder),
                id);
        return newOrder;
    }

}
