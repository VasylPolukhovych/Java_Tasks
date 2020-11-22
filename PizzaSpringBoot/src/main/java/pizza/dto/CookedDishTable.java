package pizza.dto;

public class CookedDishTable {
    private String nameDish;
    private Integer count;
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
