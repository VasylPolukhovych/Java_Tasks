package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.dto.CookedDish;
import pizza.dto.Dish;
import pizza.repository.DishRep;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DishService {
    @Autowired
    private DishRep dishRep;
    private LocalDate ld = LocalDate.now();

    public boolean isDishExists(String nameDish) {
        return dishRep.findByName(nameDish).isPresent();
    }

    public List<Dish> getAll() {
        List<Dish> obj = dishRep.findAll();
        return obj;
    }

    public Dish findByName(String name) {
        Dish opt = dishRep.findByName(name).orElse(null);
        return opt;
    }

    public Dish findCookedDishByName(String name) {
        Dish cookedDish = dishRep.findByName(name).orElse(null);
        if (!(cookedDish == null)) {
            List<CookedDish> lcd = cookedDish.getCookedDish().stream().
                    filter(cd -> cd.getCurcount() > 0
                            && cd.getDateOfMaking().plusDays(cookedDish.getShelfLife()).isAfter(ld)).collect(Collectors.toList());
            if (!(lcd.isEmpty() || lcd == null)) {
                cookedDish.setCookedDish(lcd);
            }
        }
        return cookedDish;
    }

    public List<Dish> getMenu() {
        List<Dish> obj = dishRep.findAll();
        List<Dish> result = new ArrayList<>();
        obj.forEach(x -> {
                    Dish resDish = new Dish(x.getId(), x.getName(),
                            x.getCostOfCosts(), x.getPrice(), x.getShelfLife());
                    List<CookedDish> lcd = new ArrayList<>();
                    x.getCookedDish().
                            forEach(z -> {
                                if (z.getCurcount() > 0 &&
                                        z.getDateOfMaking().plusDays(x.getShelfLife()).isAfter(ld)
                                ) {
                                    lcd.add(z);
                                }
                            });
                    if (!(lcd.isEmpty() || lcd == null)) {
                        resDish.setCookedDish(lcd);
                        result.add(resDish);
                    }
                }
        );
        return result;
    }
}
