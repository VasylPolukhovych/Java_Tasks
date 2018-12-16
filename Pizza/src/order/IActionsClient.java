package order;
import menu.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface IActionsClient {

   CookedDish findCookedDish(String nameDish, Menu menu);

   Order findOrderById(int orderId, Orders orders);

   float calcOrderSum(DishForOrder[] orderDish);

   void printOrder(int orderId, Orders orders, Menu menu);

   Order inputOrder(Menu menu);

   void printMenu(Menu menu);

   static float toCurrency(float f) {
      return new BigDecimal(f).setScale(2, RoundingMode.UP).floatValue();
   }

}
