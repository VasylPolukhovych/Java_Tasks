package pizza.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pizza.dto.DishInOrder;
import pizza.dto.Order;
import pizza.dto.mappers.OrderMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class OrderDAO {
    JdbcTemplate jdbcTemplate;

    private final String SQL_GET_ORDER = "SELECT o.id, extract(day from o.date) dd,extract(month from o.date) mm," +
            " extract(year from o.date) yy, o.tip" +
            " FROM public.order o where o.id =?";
    private final String SQL_ADD_ORDER = "INSERT INTO public.order(" +
            "date, tip)" +
            "VALUES (?, ?)";
    private final String SQL_MAX_ID = "SELECT max(id) as MAX_ID from public.order";
    private final String SQL_ADD_DISH_TO_ORDER = "INSERT INTO public.dish_in_oreder(" +
            "name_dish, id_oreder, count)" +
            "VALUES ( ?, ?, ?);";


    public OrderDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int addOrder(Order order) throws Exception {
        jdbcTemplate.update(SQL_ADD_ORDER, java.sql.Date.valueOf(order.getDate()), order.getTip());

        int orderId = (Integer) jdbcTemplate.queryForObject(SQL_MAX_ID, new Object[]{}, Integer.class);

        for (DishInOrder dishInOrder : order.getSelectedDishes()
                ) {
            jdbcTemplate.update(SQL_ADD_DISH_TO_ORDER, dishInOrder.getDish().getNameDish(),
                    orderId, dishInOrder.getCount());

        }
        return orderId;
    }


    public Order getOrder(int id, List<DishInOrder> dishInOrder) throws Exception {
        return jdbcTemplate.queryForObject(SQL_GET_ORDER, new Object[]{id}, new OrderMapper(dishInOrder));

    }

}
