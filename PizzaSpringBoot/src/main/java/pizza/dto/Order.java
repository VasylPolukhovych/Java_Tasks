package pizza.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public class Order {
    @Id
    private Long id;

    @NotEmpty(message = "Order must contain some dish")
    @MappedCollection(idColumn = "id_order", keyColumn = "id")
    private List<DishInOrder> selectedDishes;
    private LocalDate date;

    @PositiveOrZero(message = "Tip must be >= 0")
    private Integer tip;

    public Order(LocalDate orderDate, Integer tip) {
        this.date = orderDate;
        this.tip = tip;

    }

    public Order(List<DishInOrder> selectedDishes, LocalDate orderDate, Integer tip) {
        this.selectedDishes = selectedDishes;
        this.date = orderDate;
        this.tip = tip;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public List<DishInOrder> getSelectedDishes() {
        return selectedDishes;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getTip() {
        return tip;
    }

    public void setSelectedDishes(List<DishInOrder> selectedDishes) {
        this.selectedDishes = selectedDishes;
    }
}
