package pizza.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Table("dish_in_order")
public class DishInOrder {

    @Id
    private Long id;
    @NotNull(message = "Name dish is mandatory")
    @Column("name_dish")
    private String nameDish;

    @NotNull(message = "Count of dish is mandatory")
    @Positive(message = "Count of dish must be > 0")
    private Integer count;
    @Column("id_order")
    private Long idOrder;
    private String message;


    public DishInOrder(String nameDish, Integer count) {
        this.nameDish = nameDish;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameDish() {
        return nameDish;
    }

    public void setNameDish(String nameDish) {
        this.nameDish = nameDish;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


