package menu;

import common.Identifier;
import java.time.LocalDate;

public class CookedDish {
  private Identifier idCookedDish = new Identifier();
  private Long count;
  private LocalDate dateOfMaking;
  private Dish dish;

  public CookedDish() {
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
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

  public Identifier getIdCookedDish() {
    return idCookedDish;
  }

}
