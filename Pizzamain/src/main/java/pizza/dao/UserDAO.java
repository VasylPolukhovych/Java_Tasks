package pizza.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDAO {
    JdbcTemplate jdbcTemplate;

    private final String SQL_GET_USER = "SELECT d.id " +
            "FROM public.users d " +
            "where d.user_nm =?";

    public UserDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public String getUser(String name) throws Exception {
        List<String> results =
                jdbcTemplate.queryForList(SQL_GET_USER, new Object[]{name}, String.class);
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }

    }
}


