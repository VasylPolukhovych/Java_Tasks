package pizza;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    //private static InputData inputData = new InputDataOfOrder();
    private static CurrentMenuDAO currentMenu;

    public static void main(String[] args) throws Exception {


        Connection conn = null;

        String confFile = "applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(confFile);
        PGSimpleDataSource ds = (PGSimpleDataSource) context.getBean("dataSource");
        InputData input = (InputData) context.getBean("input");
        Reports report = (Reports) context.getBean("reports");

        try {

            conn = ds.getConnection();
            currentMenu = new CurrentMenuDAO(conn);
            List<CookedDish> menu = currentMenu.getCurrentMenu();

            report.printMenu(menu);
            boolean isContinue = true;
            while (isContinue) {
               ServeClient serveClient = (ServeClient) context.getBean("serveClient", input, menu, conn);
                serveClient.getNewOrder();
                isContinue = input.isInputEnd("Do you want to continue ?");
            }
            report.printDishs(currentMenu.getDishes(), "All cooked dishes");
            report.printDishs(currentMenu.getDishes(), "All spoiled dishes");
            report.printOrder(2, null);
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