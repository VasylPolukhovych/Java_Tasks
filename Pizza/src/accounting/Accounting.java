package accounting;

import order.*;
import menu.*;

import java.util.*;

public class Accounting implements IAccounting {

    public void minusDish(LinkedList<CookedDish> menu, Order ord) {
        for (Map.Entry<String, SelectedDishes> o : ord.getSelectedDishes().entrySet()) {
            int cnt = o.getValue().getCount();
            LinkedList<CookedDish> forDel = new LinkedList<>();
            for (CookedDish m : menu) {

                if (o.getKey().equalsIgnoreCase(m.getDish().getNameDish())) {
                    m.setCount(m.getCount() - cnt);
                    if (m.getCount() <= 0) {
                        cnt = m.getCount() * (-1);
                        forDel.add(m);
                    }
                }
            }
            menu.removeAll(forDel);
        }
    }

    public Integer addOrderToOrders(Order ord, HashMap<Integer, Order> orders) {
        Integer orderKey = 1;
        if (!(orders.entrySet().isEmpty())) {
            orderKey = Collections.max(orders.keySet()) + 1;
        }
        orders.put(orderKey, ord);
        return orderKey;
    }


}