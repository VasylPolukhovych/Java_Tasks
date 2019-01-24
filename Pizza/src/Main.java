import accounting.BasicAccounting;
import input.InputData;
import input.InputDataOfOrder;
import menu.CurrentMenu;
import menu.Menu;
import order.ActionsWithClient;
import order.MainActionsWithClient;
import output.Reports;

public class Main {


    private static BasicAccounting accounting = new BasicAccounting();
    private static Menu menu = new CurrentMenu();
    private static ActionsWithClient actionsWithClient = new MainActionsWithClient();
    private static Reports reports =new Reports();
    private static InputData inputData = new InputDataOfOrder();


    public static void main(String[] args) {
        menu.addDishs(6);
        menu.cookDishs(5,accounting);
        reports.printMenu(menu);
        accounting.fillAllCookedDishsByMenu(menu);
        actionsWithClient.serveClient(menu,inputData,accounting);
        reports.printMenu(menu);
        reports.printDishs(accounting.getAllCookedDishes(),"All cooked dishes");
        accounting.disposeOfOverdueDishes(menu);
        reports.printDishs(accounting.getSpoiledDishes(),"All spoiled dishes");
    }


}