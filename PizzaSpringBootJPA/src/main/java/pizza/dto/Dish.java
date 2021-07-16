package pizza.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dish")
public class Dish implements Comparable<Dish> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Name dish cannot be null")
    @Size(min = 4, message = "Name dish should have atleast 4 characters")
    private String name;
    @Column(name = "cost_of_cost")
    private Double costOfCosts;
    private Double price;
    @Column(name = "shelf_life")
    private Integer shelfLife;
    @OneToMany(mappedBy = "idDish")
    private List<CookedDish> cookedDish;

    public Dish(Long id, String name, Double costOfCosts, Double price, Integer shelfLife) {
        this.id = id;
        this.name = name;
        this.costOfCosts = costOfCosts;
        this.price = price;
        this.shelfLife = shelfLife;
    }

    public Dish() {
    }

    public List<CookedDish> getCookedDish() {
        return cookedDish;
    }

    public void setCookedDish(List<CookedDish> cookedDish) {
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
