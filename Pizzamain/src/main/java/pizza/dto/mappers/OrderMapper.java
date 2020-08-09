package pizza.dto.mappers;

import org.springframework.jdbc.core.RowMapper;
import pizza.dto.DishInOrder;
import pizza.dto.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderMapper implements RowMapper<Order> {
    private List<DishInOrder> dio;

    public OrderMapper(List<DishInOrder> dio) {
        this.dio = dio;
    }

    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Order(dio,
                LocalDate.of(resultSet.getInt("YY"),
                        resultSet.getInt("MM"),
                        resultSet.getInt("DD")),
                resultSet.getInt("TIP"));

    }
}
