package demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class Pizza {
    @Length(min = 3, max = 6)
    private String name;
    private Double price;
    private Integer count;

    public Pizza() {
    }

    public Pizza(String name, Double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public Double getPrice() {
        return price;
    }

    @JsonProperty
    public Integer getCount() {
        return count;
    }

}