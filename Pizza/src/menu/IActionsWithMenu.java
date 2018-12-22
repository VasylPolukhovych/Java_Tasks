package menu;

import java.util.HashSet;
import java.util.LinkedList;

public interface IActionsWithMenu{

    Dish getDishByName(String name, int id,HashSet<Dish> dishs);
    CookedDish findMenuItem(String nameDish,LinkedList<CookedDish> menu);
    void printMenu(LinkedList<CookedDish> menu);
    int getNextIdForMenu (LinkedList<CookedDish> menu);
}
