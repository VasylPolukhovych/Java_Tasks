package pizza.dto.mappers;

import org.springframework.jdbc.core.RowMapper;
import pizza.dto.Dish;
import pizza.dto.DishInOrder;
import pizza.dto.common.Money;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class DishInOrderMapper implements RowMapper<DishInOrder> {
    public DishInOrder mapRow(ResultSet resultSet, int i) throws SQLException {

        Dish dish = new Dish(resultSet.getString("NAME"),
                new Money(resultSet.getDouble("COST_OF_COST")),
                new Money(resultSet.getDouble("PRICE")),
                Duration.ofDays(resultSet.getInt("SHELF_LIFE")));
        return new DishInOrder(resultSet.getInt("COUNT"), dish);

    }
}
