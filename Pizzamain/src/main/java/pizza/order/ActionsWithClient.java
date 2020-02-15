package pizza.order;
import pizza.accounting.Accounting;
import pizza.exception.OrderNotfoundException;
import pizza.input.InputData;
import pizza.menu.Menu;

public interface ActionsWithClient {

   void serveClient(Menu menu, InputData inputData, Accounting accounting);

}
