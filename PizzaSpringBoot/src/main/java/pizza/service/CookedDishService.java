package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.dao.CookedDishDAO;
import pizza.dto.CookedDish;
import pizza.dto.CookedDishTable;

import java.util.List;

@Service
public class CookedDishService {
    @Autowired
    private CookedDishDAO cookedDishDAO;

    public List<CookedDish> getMenu() throws Exception {
        List<CookedDish> obj = cookedDishDAO.getCurrentMenu();

        return obj;
    }

    public boolean addCookedDish(CookedDishTable cookedDishTable) {
        cookedDishDAO.addCookedDish(cookedDishTable);
        return true;
    }
}



