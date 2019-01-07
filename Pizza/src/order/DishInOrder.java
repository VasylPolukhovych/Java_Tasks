package order;

import menu.Dish;

public class DishInOrder {
    private Dish dish;
    private Long count;

    public DishInOrder() {
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }


}


