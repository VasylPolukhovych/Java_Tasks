package order;
import accounting.Accounting;
import input.InputData;
import menu.Menu;

import java.util.Map;


public interface ActionsWithClient {

   Order fillOrder(Map<String, Long> orderDetails, Long tip, Menu menu);
   void serveClient(Menu menu, InputData inputData, Accounting accounting);

}
