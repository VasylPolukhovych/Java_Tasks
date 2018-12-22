package order;
import menu.*;

import java.util.HashMap;
import java.util.LinkedList;

public interface IActionsWithClient {

   float calcOrderSum(HashMap<String,SelectedDishes> selectedDishes);

   Order inputOrder(LinkedList<CookedDish> menu);

   void printOrder(Integer orderId, HashMap<Integer,Order> orders);
}
