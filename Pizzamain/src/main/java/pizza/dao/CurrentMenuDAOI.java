package pizza.dao;

import pizza.dto.CookedDish;

import java.util.List;

public interface CurrentMenuDAOI {

    List<CookedDish> getCurrentMenu() throws Exception;
    List<CookedDish> getDishes() throws Exception;
    void setCount (int id,int count) throws Exception;
    int getCurrentCount(int id) throws Exception;
    List<CookedDish> getCookedDishesByName(String name) throws Exception;

}
