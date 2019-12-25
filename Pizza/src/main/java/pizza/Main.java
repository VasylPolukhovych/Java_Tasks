package pizza;
import pizza.accounting.BasicAccounting;
import pizza.common.Identifier;
import pizza.exception.OrderNotfoundException;
import pizza.input.InputData;
import pizza.input.InputDataOfOrder;
import pizza.menu.CurrentMenu;
import pizza.menu.Menu;
import pizza.order.ActionsWithClient;
import pizza.order.MainActionsWithClient;
import pizza.output.Reports;

import static java.awt.SystemColor.menu;

public class Main {


    private static BasicAccounting accounting = new BasicAccounting();
    private static Menu menu = new CurrentMenu();
    private static ActionsWithClient actionsWithClient = new MainActionsWithClient();
    private static Reports reports = new Reports();
    private static InputData inputData = new InputDataOfOrder();


    public static void main(String[] args)  {
        menu.addDishs(7);
        menu.cookDishs(6);
        reports.printMenu(menu);
        accounting.fillAllCookedDishsByMenu(menu);
        actionsWithClient.serveClient(menu, inputData, accounting);
        reports.printMenu(menu);
        reports.printDishs(accounting.getAllCookedDishes(), "All cooked dishes");
        accounting.disposeOfOverdueDishes(menu);
        reports.printDishs(accounting.getSpoiledDishes(), "All spoiled dishes");
        reports.printOrder(new Identifier(),accounting);
    }


}