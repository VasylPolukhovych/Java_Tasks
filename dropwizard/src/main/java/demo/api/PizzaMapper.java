package demo.api;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PizzaMapper implements ResultSetMapper<Pizza> {


    public Pizza map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Pizza(resultSet.getString("NAME"),
                resultSet.getDouble("PRICE"),
                resultSet.getInt("COUNT"),
                resultSet.getInt("ID")
        );
    }
}


