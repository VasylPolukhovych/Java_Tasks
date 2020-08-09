package pizza;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pizza.config.ApplConfig;
import pizza.dao.CurrentMenuDAO;
import pizza.dao.OrderDAO;
import pizza.dto.CookedDish;
import pizza.input.InputData;
import pizza.output.Reports;
import pizza.service.ServeClient;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        ApplicationContext context
                = new AnnotationConfigApplicationContext(ApplConfig.class);

        Reports report = (Reports) context.getBean("report");
        InputData input = (InputData) context.getBean("input");
        CurrentMenuDAO menuDAO = (CurrentMenuDAO) context.getBean(CurrentMenuDAO.class);
        OrderDAO orderDAO = (OrderDAO) context.getBean(OrderDAO.class);
        report.printMenu(menuDAO.getCurrentMenu());
        boolean isContinue = true;
        while (isContinue) {
            ServeClient serveClient = (ServeClient) context.getBean("serveClient",
                    input, report, orderDAO, menuDAO);
            serveClient.getNewOrder();
            isContinue = input.isInputEnd("Do you want to continue ?");
        }
        report.printDishs(menuDAO.getDishes(), "All cooked dishes");
        report.printDishs(menuDAO.getDishes(), "All spoiled dishes");
        report.printOrder(2, null);
    }
}