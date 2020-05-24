package pizza.dto;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private List<DishInOrder> selectedDishes;
    private LocalDate date;
    private int tip;
    public Order(List<DishInOrder> selectedDishes, LocalDate orderDate, int tip) {
        this.selectedDishes = selectedDishes;
        this.date = orderDate;
        this.tip = tip;
    }

    public List<DishInOrder> getSelectedDishes() {
        return selectedDishes;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTip() {
        return tip;
    }


}
