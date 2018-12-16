package accounting;
import order.*;
import menu.*;

public interface IAccounting {
    Orders addElement(Orders orders, Order element);
    void minusDish (Menu menu, Order ord);
}
