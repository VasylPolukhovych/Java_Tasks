package pizza.exception;

import org.springframework.beans.factory.annotation.Autowired;
import pizza.dto.CookedDishTable;
import pizza.dto.Dish;
import pizza.dto.Order;
import pizza.service.DishService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DishValidator implements ConstraintValidator<IsDishExists, CookedDishTable> {

    @Autowired
    private DishService dishService;

    @Override
    public void initialize(IsDishExists isDishExists) {

    }

    @Override
    public boolean isValid(CookedDishTable cookedDishTable, ConstraintValidatorContext ctx) {

        if  (!dishService.isDishExists(cookedDishTable.getNameDish())) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    "Unfortunatelly, but we do not cook "+cookedDishTable.getNameDish())
                    .addPropertyNode("nameDish").addConstraintViolation();
            return false;
        }
        return true;
    }
}
