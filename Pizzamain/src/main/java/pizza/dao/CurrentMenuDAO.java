package pizza.dao;

import pizza.dto.CookedDish;
import pizza.dto.Dish;
import pizza.dto.common.Money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class CurrentMenuDAO {
    private Connection conn;

    public CurrentMenuDAO(Connection conn) {
        this.conn = conn;
    }

    public List<CookedDish> getCookedDishes(ResultSet resultSet) throws SQLException {
        List<CookedDish> cookedDishes = new ArrayList<>();
        while (resultSet.next()) {
            Dish dish = new Dish(resultSet.getString("NAME_DISH"),
                    new Money(resultSet.getDouble("COST_OF_COST")),
                    new Money(resultSet.getDouble("PRICE")),
                    Duration.ofDays(resultSet.getInt("SHELF_LIFE")));
            CookedDish cookedDish = new CookedDish(
                    resultSet.getInt("ID"),
                    dish,
                    resultSet.getInt("COUNT"),
                    resultSet.getInt("CURRENT_COUNT"),
                    LocalDate.of(resultSet.getInt("YY")
                            , resultSet.getInt("MM")
                            , resultSet.getInt("DD")));
            cookedDishes.add(cookedDish);

        }
        return cookedDishes;
    }

    public List<CookedDish> getCurrentMenu() throws Exception {

        try (PreparedStatement st = conn.prepareStatement(
                "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
                        ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
                        " extract(year from cd.date_of_making) yy" +
                        " FROM public.cooked_dish cd " +
                        " join public.dish d on d.name=cd.name_dish " +
                        " where cd.date_of_making +d.shelf_life >current_date" +
                        " and cd.current_count > 0; ");
             ResultSet rs = st.executeQuery();
        ) {
            return getCookedDishes(rs);
        }
    }

    public List<CookedDish> getDishes() throws Exception {
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
                        ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
                        " extract(year from cd.date_of_making) yy" +
                        " FROM public.cooked_dish cd " +
                        " join public.dish d on d.name=cd.name_dish ");
             ResultSet rs = st.executeQuery();
        ) {
            return getCookedDishes(rs);
        }
    }

    public List<CookedDish> getCookedDishesByName(String name) throws Exception {

        try (PreparedStatement st = conn.prepareStatement(
                "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
                        ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
                        " extract(year from cd.date_of_making) yy" +
                        " FROM public.cooked_dish cd " +
                        " join public.dish d on d.name=cd.name_dish " +
                        " where cd.date_of_making +d.shelf_life >current_date" +
                        " and cd.current_count > 0 and d.name = ?; ");
        ) {
            st.setString(1, name);
            try (ResultSet rs = st.executeQuery();
            ) {
                return getCookedDishes(rs);
            }
        }
    }

    public int getCurrentCount(int id) throws Exception {
        int currentCount;

        try (PreparedStatement st = conn.prepareStatement(
                "SELECT cd.current_count FROM public.cooked_dish cd " +
                        " WHERE cd.id =?");) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery();) {
                rs.next();
                currentCount = rs.getInt("CURRENT_COUNT");
                return currentCount;
            }
        }
    }

    public void setCount(int id, int count) throws Exception {
        try (PreparedStatement st = conn.prepareStatement(
                "UPDATE public.cooked_dish" +
                        " SET  current_count = ?" +
                        " WHERE id = ?;");) {
            st.setInt(1, count);
            st.setInt(2, id);
            st.executeUpdate();
        }
    }
}
