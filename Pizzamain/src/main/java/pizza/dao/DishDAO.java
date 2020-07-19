package pizza.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pizza.dto.Dish;
import pizza.dto.DishInOrder;
import pizza.dto.common.Money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class DishDAO {
    @Autowired
    private Connection conn;

    public DishDAO() {
    }

    public List<DishInOrder> getDishesInOrder(int idOrder) throws Exception {
        DishInOrder dishInOrder;
        List<DishInOrder> dishesInOrder = new ArrayList();
        Dish dish;
        try (PreparedStatement stDishInOrder = conn.prepareStatement(
                "SELECT d.name, d.cost_of_cost, d.price, d.shelf_life,dor.count" +
                        "FROM public.dish d" +
                        "join public.dish_in_oreder dor" +
                        "on d.name=dor.name_dish" +
                        "where dor.id_oreder =?");) {
            stDishInOrder.setInt(1, idOrder);

            try (ResultSet rsDishInOrder = stDishInOrder.executeQuery();) {
                while (rsDishInOrder.next()) {
                    dish = new Dish(rsDishInOrder.getString("NAME"),
                            new Money(rsDishInOrder.getDouble("COST_OF_COST")),
                            new Money(rsDishInOrder.getDouble("PRICE")),
                            Duration.ofDays(rsDishInOrder.getInt("SHELF_LIFE")));
                    dishInOrder = new DishInOrder(rsDishInOrder.getInt("COUNT"), dish);
                    dishesInOrder.add(dishInOrder);
                }
                return dishesInOrder;
            }
        }
    }
}
