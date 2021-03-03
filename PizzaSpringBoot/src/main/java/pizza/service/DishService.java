package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.dao.DishDAO;

@Service
public class DishService {
    @Autowired
    private DishDAO DishDAO;

    public boolean isDishExists(String nameDish)  {
        return DishDAO.isDishExist(nameDish);
    }
}
