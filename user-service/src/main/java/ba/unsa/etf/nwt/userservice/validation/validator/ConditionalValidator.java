package ba.unsa.etf.nwt.userservice.validation.validator;

import ba.unsa.etf.nwt.userservice.validation.annotation.Conditional;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

import static org.springframework.util.ObjectUtils.isEmpty;

public class ConditionalValidator implements ConstraintValidator<Conditional, Object> {

    private String property;
    private String[] required;
    private String message;
    private String[] values;

    @Override
    public void initialize(Conditional requiredIfChecked) {
        property = requiredIfChecked.property();
        required = requiredIfChecked.required();
        message = requiredIfChecked.message();
        values = requiredIfChecked.values();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object validatedObject, ConstraintValidatorContext context) {
        Object actualValue = BeanUtils.getProperty(validatedObject, property);
        if (!Arrays.asList(values).contains(actualValue)) return true;
        boolean valid = true;
        for (String prop : required) {
            Object requiredValue = BeanUtils.getProperty(validatedObject, prop);
            if (requiredValue == null || isEmpty(requiredValue)) {
                valid = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addPropertyNode(prop).addConstraintViolation();
            }
        }
        return valid;
    }
}
