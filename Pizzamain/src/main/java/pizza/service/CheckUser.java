package pizza.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pizza.dao.UserDAO;
import pizza.input.InputData;

public class CheckUser {
    private InputData input;
    private UserDAO userDAO;

    public CheckUser(InputData input, UserDAO userDAO) {
        this.input = input;
        this.userDAO = userDAO;
    }

    public String getCurrentUser() {
        return input.inputString("Please input user name :");
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String getUseFromDB(String user) throws Exception {
        return userDAO.getUser(user);
    }


}
