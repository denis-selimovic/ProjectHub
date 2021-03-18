package ba.unsa.etf.nwt.userservice.validation.annotation;

import ba.unsa.etf.nwt.userservice.validation.validator.FieldMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FieldMatchValidator.class})
public @interface FieldMatch {
    String message() default "The fields must match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String first();
    String second();
}
