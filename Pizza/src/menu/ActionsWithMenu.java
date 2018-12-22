package menu;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class ActionsWithMenu implements IActionsWithMenu {


    public ActionsWithMenu() {

    }


    public Dish getDishByName(String name, int id, HashSet<Dish> dishs) {
        Iterator<Dish> it = dishs.iterator();
        while (it.hasNext()) {
            Dish d = it.next();
            if (d.getNameDish().equals(name) && d.getIdDish() != id) {
                return d;
            }
        }
        return null;
    }

    public CookedDish findMenuItem(String nameDish, LinkedList<CookedDish> menu) {
        LocalDate lD = LocalDate.now();
        Iterator<CookedDish> it = menu.iterator();
        while (it.hasNext()) {
            CookedDish menuItem = it.next();
            Dish dsh = menuItem.getDish();
            if (dsh.getNameDish().equalsIgnoreCase(nameDish)) {
                long plusDay = dsh.getExpirationDate();
                int compDt = menuItem.getDateOfMaking().plusDays(plusDay).compareTo(lD);
                if (compDt > 0 && menuItem.getCount() > 0) {
                    return menuItem;
                }
            }
        }
        return null;
    }

    public void printMenu(LinkedList<CookedDish> menu) {
        System.out.println("*****************************************");
        System.out.println("Menu");
        Iterator<CookedDish> it = menu.iterator();
        while (it.hasNext()) {
            CookedDish c = it.next();
            System.out.println("Dish - " + c.getDish().getNameDish() + ". Count - " + c.getCount() + ". Price - " + c.getDish().getPrice());
        }
        System.out.println("*****************************************");
    }


    public int getNextIdForMenu(LinkedList<CookedDish> menu) {

        if (menu.isEmpty()) {
            return 1;
        }
        return menu.getLast().getIdCookedDish() + 1;
    }
}
