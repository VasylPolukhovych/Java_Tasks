package pizza.dao;

import pizza.dto.CookedDish;
import pizza.dto.Dish;
import pizza.dto.common.Money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class CurrentMenuDAO implements CurrentMenuDAOI {
    private Connection conn;

    public CurrentMenuDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<CookedDish> getCurrentMenu() throws Exception {
        List<CookedDish> currMenu = new ArrayList<CookedDish>();

        PreparedStatement st = conn.prepareStatement(
                "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
                        ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
                        " extract(year from cd.date_of_making) yy" +
                        " FROM public.cooked_dish cd " +
                        " join public.dish d on d.name=cd.name_dish " +
                        " where cd.date_of_making +d.shelf_life >current_date" +
                        " and cd.current_count > 0; ");
        ResultSet rs = st.executeQuery();

        CookedDish cookedDish;
        Dish dish;
        while (rs.next()) {
            dish = new Dish(rs.getString("NAME_DISH"),
                    new Money(rs.getDouble("COST_OF_COST")),
                    new Money(rs.getDouble("PRICE")),
                    Duration.ofDays(rs.getInt("SHELF_LIFE")));
            cookedDish = new CookedDish(
                    rs.getInt("ID"),
                    dish,
                    rs.getInt("COUNT"),
                    rs.getInt("CURRENT_COUNT"),
                    LocalDate.of(rs.getInt("YY"), rs.getInt("MM"), rs.getInt("DD"))
            );
            currMenu.add(cookedDish);
        }
        rs.close();

        return currMenu;
    }

    @Override
    public List<CookedDish> getDishes() throws Exception {
        List<CookedDish> dishes = new ArrayList<CookedDish>();

        PreparedStatement st = conn.prepareStatement(
                "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
                        ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
                        " extract(year from cd.date_of_making) yy" +
                        " FROM public.cooked_dish cd " +
                        " join public.dish d on d.name=cd.name_dish ");
        ResultSet rs = st.executeQuery();

        CookedDish cookedDish;
        Dish dish;
        while (rs.next()) {
            dish = new Dish(rs.getString("NAME_DISH"),
                    new Money(rs.getDouble("COST_OF_COST")),
                    new Money(rs.getDouble("PRICE")),
                    Duration.ofDays(rs.getInt("SHELF_LIFE")));
            cookedDish = new CookedDish(
                    rs.getInt("ID"),
                    dish,
                    rs.getInt("COUNT"),
                    rs.getInt("CURRENT_COUNT"),
                    LocalDate.of(rs.getInt("YY"), rs.getInt("MM"), rs.getInt("DD"))
            );
            dishes.add(cookedDish);
        }
        rs.close();
        return dishes;
    }

    @Override
    public List<CookedDish> getCookedDishesByName(String name) throws Exception {
        List<CookedDish> someDishInMenu = new ArrayList<CookedDish>();

        PreparedStatement st = conn.prepareStatement(
                "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
                        ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
                        " extract(year from cd.date_of_making) yy" +
                        " FROM public.cooked_dish cd " +
                        " join public.dish d on d.name=cd.name_dish " +
                        " where cd.date_of_making +d.shelf_life >current_date" +
                        " and cd.current_count > 0 and d.name = ?; ");
        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        CookedDish cookedDish;
        Dish dish;
        while (rs.next()) {
            dish = new Dish(rs.getString("NAME_DISH"),
                    new Money(rs.getDouble("COST_OF_COST")),
                    new Money(rs.getDouble("PRICE")),
                    Duration.ofDays(rs.getInt("SHELF_LIFE")));
            cookedDish = new CookedDish(
                    rs.getInt("ID"),
                    dish,
                    rs.getInt("COUNT"),
                    rs.getInt("CURRENT_COUNT"),
                    LocalDate.of(rs.getInt("YY"), rs.getInt("MM"), rs.getInt("DD"))
            );
            someDishInMenu.add(cookedDish);
        }
        rs.close();

        return someDishInMenu;
    }

    public int getCurrentCount(int id) throws Exception {
        int currentCount;

        PreparedStatement st = conn.prepareStatement(
                "SELECT cd.current_count FROM public.cooked_dish cd " +
                        " WHERE cd.id =?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        rs.next();
        currentCount = rs.getInt("CURRENT_COUNT");
        rs.close();
        return currentCount;
    }

    @Override
    public void setCount(int id, int count) throws Exception {
        PreparedStatement st;
        st = conn.prepareStatement(
                "UPDATE public.cooked_dish" +
                        " SET  current_count = ?" +
                        " WHERE id = ?;");
        st.setInt(1, count);
        st.setInt(2, id);
        st.executeUpdate();

    }
}
