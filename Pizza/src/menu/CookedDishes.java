package menu;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CookedDishes {

    private List<CookedDish> cookedDishes = new LinkedList<>();

    public CookedDishes() {
    }

    public List<CookedDish> getCookedDishes() {
        return cookedDishes;
    }

    public void setCookedDishes(List<CookedDish> cookedDishes) {
        this.cookedDishes = cookedDishes;
    }

    public boolean isExpirationDateExpired (LocalDate date,Long expirationDate){
        LocalDate currentDate = LocalDate.now();
        if (date.plusDays(expirationDate).compareTo(currentDate)>0) {return false;}
        return true;
    }

}


