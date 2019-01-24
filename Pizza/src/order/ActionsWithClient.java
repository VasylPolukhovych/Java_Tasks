package order;
import accounting.Accounting;
import input.InputData;
import menu.Menu;

public interface ActionsWithClient {

   void serveClient(Menu menu, InputData inputData, Accounting accounting);

}
