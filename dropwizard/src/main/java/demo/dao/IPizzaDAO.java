package demo.dao;

import demo.api.Pizza;
import demo.api.PizzaMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(PizzaMapper.class)
public interface IPizzaDAO {

    @SqlQuery("select * from pizza")
    List<Pizza> getAll();

    @SqlQuery("select * from pizza where NAME = :name")
    Pizza findByName(@Bind("name") String name);

    @SqlQuery("select * from PIZZA where ID = :id")
    Pizza findById(@Bind("id") int id);

    @SqlUpdate("delete from pizza where ID = :id")
    int deleteById(@Bind("id") int id);

    @SqlUpdate("update pizza set COUNT = :count where ID = :id")
    int update(@BindBean Pizza pizza);

    @SqlUpdate("insert into pizza (ID, NAME, PRICE, COUNT) values (:id, :name, :price, :count)")
    int insert(@BindBean Pizza pizza);
}

