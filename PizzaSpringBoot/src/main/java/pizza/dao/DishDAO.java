package pizza.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
@Repository

public class DishDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String SQL_IS_DISH_EXISTS = "SELECT count(d.name) as cnt " +
            " FROM public.dish d " +
            " where d.name = ?";


    public boolean isDishExist(String name) {
        int cnt = jdbcTemplate.queryForObject(SQL_IS_DISH_EXISTS, Integer.class, name);
        if (cnt > 0) {
            return true;
        } else {
            return false;
        }
    }
}
