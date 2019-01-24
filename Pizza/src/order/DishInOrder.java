package order;

import menu.Dish;

public class DishInOrder extends Dish {
    private int count;

    public DishInOrder(int count,Dish dish) {
        super(dish.getNameDish(),dish.getCostOfCosts(),dish.getPrice(),dish.getExpirationDate());
        this.count =count;
    }
    public int getCount() {
        return count;
    }

}


