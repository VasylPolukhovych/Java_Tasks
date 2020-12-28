package pizza.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pizza.dto.CookedDish;
import pizza.dto.CookedDishTable;
import pizza.dto.mappers.CookedDishMapper;

import java.util.List;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
@Repository
public class CookedDishDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String SQL_GET_MENU = "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
            ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
            " extract(year from cd.date_of_making) yy" +
            " FROM public.cooked_dish cd " +
            " join public.dish d on d.name=cd.name_dish " +
            " where cd.date_of_making +d.shelf_life >current_date" +
            " and cd.current_count > 0 ";
    private final String SQL_GET_DISHES = "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
            ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
            " extract(year from cd.date_of_making) yy" +
            " FROM public.cooked_dish cd " +
            " join public.dish d on d.name=cd.name_dish";
    private final String SQL_ADD_COOKED_DISH = "INSERT INTO public.cooked_dish( " +
            "name_dish, count, current_count, date_of_making) " +
            "VALUES (?, ?, ?, current_date);";
    private final String SQL_GET_ID = "SELECT max(id) FROM cooked_dish";
    private final String SQL_UPDATE_COUNT = "UPDATE public.cooked_dish" +
            " SET  current_count = ?" +
            " WHERE id = ?";
    private final String SQL_GET_DISHES_BY_NAME = "SELECT cd.id,cd.name_dish, cd.count, cd.current_count, d.cost_of_cost" +
            ",d.price,d.shelf_life,extract(day from cd.date_of_making) dd,extract(month from cd.date_of_making) mm," +
            " extract(year from cd.date_of_making) yy" +
            " FROM public.cooked_dish cd " +
            " join public.dish d on d.name=cd.name_dish " +
            " where cd.date_of_making +d.shelf_life >current_date" +
            " and cd.current_count > 0 and d.name = ?";


    public List<CookedDish> getCurrentMenu() throws Exception {
        return this.jdbcTemplate.query(SQL_GET_MENU, new CookedDishMapper());
    }

    public List<CookedDish> getDishes() throws Exception {
        return jdbcTemplate.query(SQL_GET_DISHES, new CookedDishMapper());
    }

    public List<CookedDish> getCookedDishesByName(String name) throws Exception {
        return jdbcTemplate.query(SQL_GET_DISHES_BY_NAME, new Object[]{name}, new CookedDishMapper());
    }

    public void addCookedDish(CookedDishTable cookedDishT) {
        jdbcTemplate.update(SQL_ADD_COOKED_DISH, cookedDishT.getNameDish(), cookedDishT.getCount(), cookedDishT.getCurcount());
        int id = jdbcTemplate.queryForObject(SQL_GET_ID, Integer.class);
        cookedDishT.setId(id);
    }

    public void setCount(int id, int count) throws Exception {
        jdbcTemplate.update(SQL_UPDATE_COUNT, count, id);
    }

}