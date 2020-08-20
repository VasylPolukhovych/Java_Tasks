package pizza.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pizza.dto.DishInOrder;
import pizza.dto.mappers.DishInOrderMapper;

import javax.sql.DataSource;

@Repository
public class DishDAO {
    JdbcTemplate jdbcTemplate;

    private final String SQL_DISHES_IN__ORDER = "SELECT d.name, d.cost_of_cost, d.price, d.shelf_life,dor.count" +
            "FROM public.dish d" +
            "join public.dish_in_oreder dor" +
            "on d.name=dor.name_dish" +
            "where dor.id_oreder =?";

    public DishDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public DishInOrder getDishesInOrder(int idOrder) throws Exception {

        return jdbcTemplate.queryForObject(SQL_DISHES_IN__ORDER, new Object[]{idOrder}, new DishInOrderMapper());

    }
}
