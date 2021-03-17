package ba.unsa.etf.nwt.userservice.validation.annotation;

import ba.unsa.etf.nwt.userservice.validation.validator.ConditionalValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Repeatable(Conditionals.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConditionalValidator.class})
public @interface Conditional {
    String message() default "This field is required.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String property();
    String[] required();
    String[] values();
}
