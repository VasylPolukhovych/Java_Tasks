package pizza.dto;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Order must contain some dish")
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DishInOrder> selectedDishes = new ArrayList<>();
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

    public void addDishInOrder(DishInOrder dio) {
        this.selectedDishes.add(dio);
        dio.setOrder(this);
    }

    public void removeDishInOrder(DishInOrder dio) {
        this.selectedDishes.remove(dio);
        dio.setOrder(null);
    }

    public void setSelectedDishes(List<DishInOrder> selectedDishes) {
        this.selectedDishes = selectedDishes;

    }
}

