package pizza.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pizza.dto.CookedDish;
import pizza.dto.mappers.CookedDishMapper;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class CurrentMenuDAO {
    JdbcTemplate jdbcTemplate;

    private final String SQL_GET_MENU = "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
            ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
            " extract(year from cd.date_of_making) yy" +
            " FROM public.cooked_dish cd " +
            " join public.dish d on d.name=cd.name_dish " +
            " where cd.date_of_making +d.shelf_life >current_date" +
            " and cd.current_count > 0 ";
    private final String SQL_UPDATE_COUNT = "UPDATE public.cooked_dish" +
            " SET  current_count = ?" +
            " WHERE id = ?";
    private final String SQL_GET_DISHES = "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
            ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
            " extract(year from cd.date_of_making) yy" +
            " FROM public.cooked_dish cd " +
            " join public.dish d on d.name=cd.name_dish";
    private final String SQL_GET_DISHES_BY_NAME = "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
            ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
            " extract(year from cd.date_of_making) yy" +
            " FROM public.cooked_dish cd " +
            " join public.dish d on d.name=cd.name_dish " +
            " where cd.date_of_making +d.shelf_life >current_date" +
            " and cd.current_count > 0 and d.name = ?";

    public CurrentMenuDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public List<CookedDish> getCurrentMenu() throws Exception {
        return jdbcTemplate.query(SQL_GET_MENU, new CookedDishMapper());

    }

    public List<CookedDish> getDishes() throws Exception {
        return jdbcTemplate.query(SQL_GET_DISHES, new CookedDishMapper());
    }

    public List<CookedDish> getCookedDishesByName(String name) throws Exception {
        return jdbcTemplate.query(SQL_GET_DISHES_BY_NAME, new Object[]{name}, new CookedDishMapper());
    }


    public void setCount(int id, int count) throws Exception {
        jdbcTemplate.update(SQL_UPDATE_COUNT, count, id);
    }

}
