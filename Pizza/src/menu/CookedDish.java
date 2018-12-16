package menu;
import java.time.LocalDate;


public class CookedDish {
  private int idCookedDish;
  private int count;
  private LocalDate dateOfMaking;

  private Dish dish;

  public CookedDish() {
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public LocalDate getDateOfMaking() {
    return dateOfMaking;
  }

  public void setDateOfMaking(LocalDate dateOfMaking) {
    this.dateOfMaking = dateOfMaking;
  }

  public Dish getDish() {
    return dish;
  }

  public void setDish(Dish dish) {
    this.dish = dish;
  }

  public int getIdCookedDish() {
    return idCookedDish;
  }

  public void setIdCookedDish(int idCookedDish) {
    this.idCookedDish = idCookedDish;
  }
}
