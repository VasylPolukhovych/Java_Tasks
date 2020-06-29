package pizza;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pizza.config.ApplConfig;
import pizza.dao.CurrentMenuDAO;
import pizza.dto.CookedDish;
import pizza.input.InputData;
import pizza.output.Reports;
import pizza.service.ServeClient;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        ApplicationContext context
                = new AnnotationConfigApplicationContext(ApplConfig.class);
        Connection conn = (Connection) context.getBean("conn");
        try {
            Reports report = (Reports) context.getBean("report");
            InputData input = (InputData) context.getBean("input");
            CurrentMenuDAO menuDAO = (CurrentMenuDAO) context.getBean("menuDAO");
            List<CookedDish> menu = menuDAO.getCurrentMenu();
            report.printMenu(menu);
            boolean isContinue = true;
            while (isContinue) {
                ServeClient serveClient = (ServeClient) context.getBean("serveClient", input, menu);
                serveClient.getNewOrder();
                isContinue = input.isInputEnd("Do you want to continue ?");
            }
            report.printDishs(menuDAO.getDishes(), "All cooked dishes");
            report.printDishs(menuDAO.getDishes(), "All spoiled dishes");
            report.printOrder(2, null);

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