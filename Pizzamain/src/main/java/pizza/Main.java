package pizza;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pizza.config.ApplConfig;
import pizza.dao.CurrentMenuDAO;
import pizza.dao.OrderDAO;
import pizza.dao.UserDAO;
import pizza.input.InputData;
import pizza.output.Reports;
import pizza.service.ServeClient;

public class Main {
    public static void main(String[] args) throws Exception {

        ApplicationContext context
                = new AnnotationConfigApplicationContext(ApplConfig.class);
        UserDAO userDAO = (UserDAO) context.getBean(UserDAO.class);

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
        String user = input.inputString("Please input user name :");
        String userDB = userDAO.getUser(user);
        report.printDishs(user, userDB, menuDAO.getDishes(), "All cooked dishes");
        report.printDishs(user, userDB, menuDAO.getDishes(), "All spoiled dishes");
        report.printOrder(2, null);
    }
}