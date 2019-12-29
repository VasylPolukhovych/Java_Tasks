package pizza;
import pizza.accounting.BasicAccounting;
import pizza.common.Identifier;
import pizza.common.Money;
import pizza.exception.OrderNotfoundException;
import pizza.input.InputData;
import pizza.input.InputDataOfOrder;
import pizza.menu.CookedDish;
import pizza.menu.CurrentMenu;
import pizza.menu.Dish;
import pizza.menu.Menu;
import pizza.order.ActionsWithClient;
import pizza.order.MainActionsWithClient;
import pizza.output.Reports;

import java.time.Duration;
import java.time.LocalDate;

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
        CookedDish newCookedDish = new CookedDish(
                new Dish("New Pizza",new Money( 57 ), new Money(85),Duration.ofDays(30))
                ,7
                , LocalDate.now()
        );
        accounting.addCookedDish(newCookedDish);
        menu.addCookedDishToMenu(newCookedDish);

        reports.printMenu(menu);
        reports.printDishs(accounting.getAllCookedDishes(), "All cooked dishes");
        accounting.disposeOfOverdueDishes(menu);
        reports.printDishs(accounting.getSpoiledDishes(), "All spoiled dishes");
        reports.printOrder(new Identifier(),accounting);
        reports.salesRegister(accounting.getAllCookedDishes());
    }


}