package pizza.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "cooked_dish")
public class CookedDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Count of cooked dish cannot be null")
    @Positive(message = "Count of cooked dish must be greater than 0")
    private Integer count;
    @NotNull(message = "Dish name cannot be null")
    @Column(name = "current_count")
    private Integer curcount;
    @Column(name = "date_of_making")
    private LocalDate dateOfMaking;

    @JoinColumn(name = "id_dish", nullable = false)

    private Long idDish;

    public CookedDish() {
    }

    public CookedDish(Integer count, Integer curcount, LocalDate dateOfMaking, Long idDish) {
        this.count = count;
        this.curcount = curcount;
        this.dateOfMaking = dateOfMaking;
        this.idDish = idDish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCurcount() {
        return curcount;
    }

    public void setCurcount(Integer curcount) {
        this.curcount = curcount;
    }

    public LocalDate getDateOfMaking() {
        return dateOfMaking;
    }

    public void setDateOfMaking(LocalDate dateOfMaking) {
        this.dateOfMaking = dateOfMaking;
    }

    public Long getIdDish() {
        return idDish;
    }

    public void setIdDish(Long idDish) {
        this.idDish = idDish;
    }
}
