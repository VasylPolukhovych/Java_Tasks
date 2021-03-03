package pizza.exception;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DishValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsDishExists {

    String message() default "Dish name is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
