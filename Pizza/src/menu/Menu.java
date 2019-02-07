package menu;

import java.util.List;

public interface Menu {

    List<CookedDish> getCurrentMenu();

    CookedDish findCookedDishInMenuByName(String nameDish);

    int availableCount(String dishName);

    void changeCount(String nameDish, int count);

    void addDishs(int countOfDishs);

    void cookDishs(int countOfDishs);
}

