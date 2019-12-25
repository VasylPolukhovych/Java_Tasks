package pizza.order;

import pizza.menu.Dish;

public class DishInOrder {
    private Dish dish;
    private int count;

    public DishInOrder(int count, Dish dish) {
        this.dish = new Dish(dish.getNameDish(), dish.getCostOfCosts(), dish.getPrice(), dish.getShelfLife());
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Dish getDish() {
        return dish;
    }

}


