package pizza.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import pizza.exception.IsDishExists;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@IsDishExists
public class Dish implements Comparable<Dish> {
    @Id
    private Long id;
    @NotNull
    @Size(min = 4, message = "Name dish should have atleast 4 characters")
    private String name;
    @Column("cost_of_cost")
    private Double costOfCosts;
    private Double price;
    @Column("shelf_life")
    private Integer shelfLife;

    @MappedCollection(idColumn = "id_dish", keyColumn = "id")
    private List<CookedDishTable> cookedDish;

    public Dish(Long id, String name, Double costOfCosts, Double price, Integer shelfLife) {
        this.id = id;
        this.name = name;
        this.costOfCosts = costOfCosts;
        this.price = price;
        this.shelfLife = shelfLife;
    }

    public Dish() {
    }

    public List<CookedDishTable> getCookedDish() {
        return cookedDish;
    }

    public void setCookedDish(List<CookedDishTable> cookedDish) {
        this.cookedDish = cookedDish;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getCostOfCosts() {
        return costOfCosts;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getShelfLife() {
        return shelfLife;
    }

    @Override
    public int compareTo(Dish o) {
        return name.compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish1 = (Dish) o;
        return Objects.equals(id, dish1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
