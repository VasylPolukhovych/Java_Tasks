package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.dto.CookedDish;
import pizza.repository.CookedDishRep;

import java.util.List;

@Service
public class CookedDishService {
    @Autowired
    private CookedDishRep cookedDishRep;

    public CookedDish addCookedDish(CookedDish cookedDish) {
        CookedDish savedCookedDish = cookedDishRep.save(cookedDish);
        return savedCookedDish;
    }

    public List<CookedDish> findCookedDishes() {
        return cookedDishRep.findAll();
    }
}



