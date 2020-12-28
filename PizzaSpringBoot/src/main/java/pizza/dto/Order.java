package pizza.dto;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private List<DishInOrder> selectedDishes;
    private LocalDate date;
    private int tip;
    private int id;

    public Order(List<DishInOrder> selectedDishes, LocalDate orderDate, int tip, int id) {
        this.selectedDishes = selectedDishes;
        this.date = orderDate;
        this.tip = tip;
        this.id = id;
    }

    public Order(List<DishInOrder> selectedDishes, int tip) {
        this.selectedDishes = selectedDishes;
        this.tip = tip;
    }
    public Order() { }

    public List<DishInOrder> getSelectedDishes() {
        return selectedDishes;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTip() {
        return tip;
    }

    public int getId() {
        return id;
    }
}
