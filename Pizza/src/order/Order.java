package order;
import common.Money;

import java.time.LocalDate;
import java.util.HashMap;

public class Order {
    private HashMap<String, DishInOrder> selectedDishes;
    private LocalDate date;
    private Long tip;
    private Money summa;

    public Order() {
    }

    public HashMap<String, DishInOrder> getSelectedDishes() {
        return selectedDishes;
    }

    public void setSelectedDishes(HashMap<String, DishInOrder> selectedDishes) {
        this.selectedDishes = selectedDishes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTip() {
        return tip;
    }

    public void setTip(Long tip) {
        this.tip = tip;
    }

    public Money getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = new Money(summa);
    }
}
