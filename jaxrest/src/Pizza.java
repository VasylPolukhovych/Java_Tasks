import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Pizza {
    private String name;
    private Double price;
    private int count;

    public Pizza() {
    }

    public Pizza(String name, Double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}