package pizza.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Table("cooked_dish")
public class CookedDishTable {
    @Id
    private Long id;
    @NotNull
    private Integer count;
    @Column("current_count")
    private Integer curcount;
    @Column("date_of_making")
    private LocalDate dateOfMaking;
    @Column("id_dish")
    private Integer idDish;

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

    public Integer getIdDish() {
        return idDish;
    }

    public void setIdDish(Integer idDish) {
        this.idDish = idDish;
    }
}
