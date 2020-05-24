package pizza;

import pizza.dao.CurrentMenuDAO;
import pizza.dao.CurrentMenuDAOI;
import pizza.dto.CookedDish;
import pizza.input.InputData;
import pizza.input.InputDataOfOrder;
import pizza.output.Reports;
import pizza.service.ServeClient;

import java.sql.Connection;
import java.util.List;

public class Main {

    private static MyConnection myConn = new MyConnection("org.postgresql.Driver",
            "jdbc:postgresql://localhost:5432/Testdb",
            "postgres",
            "admin");

    private static Reports reports = new Reports();
    private static InputData inputData = new InputDataOfOrder();
    private static CurrentMenuDAOI currentMenu;

    public static void main(String[] args) throws Exception {
        Connection conn = myConn.getConnection();
        currentMenu = new CurrentMenuDAO(conn);
        List<CookedDish> menu = currentMenu.getCurrentMenu();
        reports.printMenu(menu);
        ServeClient serveClient = new ServeClient(inputData, menu, conn);
        serveClient.getNewOrder();

        reports.printDishs(currentMenu.getDishes(), "All cooked dishes");
        reports.printDishs(currentMenu.getDishes(), "All spoiled dishes");

        conn.close();
    }

}