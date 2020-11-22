package pizza.dto.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import pizza.dto.CookedDish;
import pizza.dto.Dish;
import pizza.dto.common.Money;

public class CookedDishMapper implements RowMapper<CookedDish> {

    public CookedDish mapRow(ResultSet resultSet, int i) throws SQLException {
        Dish dish = new Dish(resultSet.getString("NAME_DISH"),
                new Money(resultSet.getDouble("COST_OF_COST")),
                new Money(resultSet.getDouble("PRICE")),
                Duration.ofDays(resultSet.getInt("SHELF_LIFE")));


        return new CookedDish(resultSet.getInt("ID"),
                dish,
                resultSet.getInt("COUNT"),
                resultSet.getInt("CURRENT_COUNT"),
                LocalDate.of(resultSet.getInt("YY")
                        , resultSet.getInt("MM")
                        , resultSet.getInt("DD")));

    }
}

