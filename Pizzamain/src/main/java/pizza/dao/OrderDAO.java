package pizza.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pizza.dto.DishInOrder;
import pizza.dto.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

@Component
public class OrderDAO {
    @Autowired
    private Connection conn;

    public OrderDAO() {
    }

    public int addOrder(Order order) throws Exception {
        try (PreparedStatement st = conn.prepareStatement(
                "INSERT INTO public.order(" +
                        "date, tip)" +
                        "VALUES (?, ?);");) {
            st.setDate(1, java.sql.Date.valueOf(order.getDate()));
            st.setInt(2, order.getTip());
            st.executeUpdate();
        }

        int orderId = 0;
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT max(id) as MAX_ID from public.order");
             ResultSet rs = st.executeQuery();) {
            rs.next();
            orderId = rs.getInt("MAX_ID");
        }

        for (DishInOrder dishInOrder : order.getSelectedDishes()
                ) {
            try (PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO public.dish_in_oreder(" +
                            "name_dish, id_oreder, count)" +
                            "VALUES ( ?, ?, ?);");) {
                st.setString(1, dishInOrder.getDish().getNameDish());
                st.setInt(2, orderId);
                st.setInt(3, dishInOrder.getCount());
                st.executeUpdate();
            }
        }
        return orderId;
    }


    public Order getOrder(int id, List<DishInOrder> dishInOrder) throws Exception {
        Order order;
        PreparedStatement stOrder = conn.prepareStatement(
                "SELECT o.id, extract(day from o.date) dd,extract(month from o.date) mm," +
                        " extract(year from o.date) yy, o.tip" +
                        " FROM public.order o where o.id =?");
        stOrder.setInt(1, id);
        ResultSet rsOrder = stOrder.executeQuery();

        order = new Order(dishInOrder,
                LocalDate.of(rsOrder.getInt("YY"), rsOrder.getInt("MM"), rsOrder.getInt("DD")),
                rsOrder.getInt("TIP")
        );
        rsOrder.close();
        stOrder.close();
        return order;
    }

}
