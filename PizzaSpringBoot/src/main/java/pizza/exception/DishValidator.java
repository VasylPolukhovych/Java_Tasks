package pizza.exception;

import org.springframework.beans.factory.annotation.Autowired;
import pizza.dto.Dish;
import pizza.service.DishService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DishValidator implements ConstraintValidator<IsDishExists, Dish> {

    @Autowired
    private DishService dishService;

    @Override
    public void initialize(IsDishExists isDishExists) {

    }

    @Override
    public boolean isValid(Dish dish, ConstraintValidatorContext ctx) {

        if  (!dishService.isDishExists(dish.getName())) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    "Unfortunatelly, but we do not cook "+dish.getName())
                    .addPropertyNode("name").addConstraintViolation();
            return false;
        }
        return true;
    }
}
