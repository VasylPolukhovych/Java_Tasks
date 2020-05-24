package pizza.dao;

import pizza.dto.CookedDish;
import pizza.dto.Dish;
import pizza.dto.DishInOrder;
import pizza.dto.Order;
import pizza.dto.common.Money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class OrderDAO implements OrderDAOI {
    private Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int addOrder(Order order) throws Exception {
        PreparedStatement st;
        st = conn.prepareStatement(
                "INSERT INTO public.order(" +
                        "date, tip)" +
                        "VALUES (?, ?);");
        st.setDate(1, java.sql.Date.valueOf(order.getDate()));
        st.setInt(2, order.getTip());
        st.executeUpdate();


        st = conn.prepareStatement(
                "SELECT max(id) as MAX_ID from public.order");
        ResultSet rs = st.executeQuery();
        rs.next();
        int orderId=rs.getInt("MAX_ID");

        rs.close();
        for (DishInOrder dishInOrder : order.getSelectedDishes()
                ) {
            st = conn.prepareStatement(
                    "INSERT INTO public.dish_in_oreder(" +
                            "name_dish, id_oreder, count)" +
                            "VALUES ( ?, ?, ?);");
            st.setString(1, dishInOrder.getDish().getNameDish());
            st.setInt(2, orderId);
            st.setInt(3, dishInOrder.getCount());
            st.executeUpdate();

        }
        return orderId;
    }

    @Override
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
        return order;
    }

}
