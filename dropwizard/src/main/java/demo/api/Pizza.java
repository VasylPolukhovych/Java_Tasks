package demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class Pizza {
    @Length(min = 3, max = 6)
    private String name;
    private double price;
    private int count;
    private int id;

    public Pizza() {
    }

    public Pizza(String name, double price, int count, int id) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.id = id;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public double getPrice() {
        return price;
    }

    @JsonProperty
    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza)) return false;

        Pizza that = (Pizza) o;

        if (getId() != that.getId()) return false;
        if (!getName().equals(that.getName())) return false;

        return true;
    }
}