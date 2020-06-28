package pizza;

import org.postgresql.ds.PGSimpleDataSource;
import pizza.dao.CurrentMenuDAO;
import pizza.dto.CookedDish;
import pizza.input.InputData;
import pizza.input.InputDataOfOrder;
import pizza.output.Reports;
import pizza.service.ServeClient;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static Reports reports = new Reports();
    private static InputData inputData = new InputDataOfOrder();
    private static CurrentMenuDAO currentMenu;

    public static void main(String[] args) throws Exception {

        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerNames(new String[]{"localhost"});
        source.setDatabaseName("Testdb");
        source.setUser("postgres");
        source.setPassword("admin");

        Connection conn = null;
        try {
            conn = source.getConnection();
            currentMenu = new CurrentMenuDAO(conn);
            List<CookedDish> menu = currentMenu.getCurrentMenu();
            reports.printMenu(menu);
            boolean isContinue = true;
            while (isContinue) {
                ServeClient serveClient = new ServeClient(inputData, menu, conn);
                serveClient.getNewOrder();
                isContinue = inputData.isInputEnd("Do you want to continue ?");
            }
            reports.printDishs(currentMenu.getDishes(), "All cooked dishes");
            reports.printDishs(currentMenu.getDishes(), "All spoiled dishes");
            reports.printOrder(2, null);
            conn.close();

        } catch (SQLException e) {
            System.out.println("SQL Error code " + e.getErrorCode() + ". " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}