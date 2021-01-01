package pizza.dto;

import pizza.exception.IsDishExists;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@IsDishExists
public class CookedDishTable {
    @NotNull
    @Size(min = 4, message = "Name dish should have atleast 4 characters")
    private String nameDish;
    @NotNull
    private Integer count;
    @NotNull
    private Integer curcount;
    private Integer id;

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

    public Integer getCurcount() {
        return curcount;
    }

    public void setCurcount(Integer curcount) {
        this.curcount = curcount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
