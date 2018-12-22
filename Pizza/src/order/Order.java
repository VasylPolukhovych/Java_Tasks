package order;
import javafx.collections.transformation.SortedList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeSet;


public class Order {
    private HashMap<String,SelectedDishes> selectedDishes;
    private LocalDate date;
    private int tip;
    private float summa;

    public Order() {
    }


    public HashMap<String, SelectedDishes> getSelectedDishes() {
        return selectedDishes;
    }

    public void setSelectedDishes(HashMap<String, SelectedDishes> selectedDishes) {
        this.selectedDishes = selectedDishes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public float getSumma() {
        return summa;
    }

    public void setSumma(float summa) {
        this.summa = summa;
    }
}
