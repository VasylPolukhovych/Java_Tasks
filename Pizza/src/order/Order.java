package order;

import common.Money;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<String, DishInOrder> selectedDishes;
    private LocalDate date;
    private int tip;
    private Money summa;

    public Order(HashMap<String, DishInOrder> selectedDishes, LocalDate orderDate, int tip) {
        this.selectedDishes = selectedDishes;
        this.date = orderDate;
        this.tip = tip;
        calcOrderSum();
    }

    private void calcOrderSum() {
        Money summa = new Money(0.00);
        for (Map.Entry<String, DishInOrder> item : selectedDishes.entrySet()) {
            summa = summa.add(item.getValue().getDish().getPrice().multiply(item.getValue().getCount()));
        }
        this.summa = summa;
    }

    public Map<String, DishInOrder> getSelectedDishes() {
        return Collections.unmodifiableMap(selectedDishes);
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTip() {
        return tip;
    }

    public Money getSumma() {
        return summa;
    }

}
