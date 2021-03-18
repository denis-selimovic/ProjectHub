package ba.unsa.etf.nwt.userservice.validation.validator;

import ba.unsa.etf.nwt.userservice.validation.annotation.FieldMatch;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String first;
    private String second;
    private String message;

    @Override
    public void initialize(FieldMatch fieldMatch) {
        first = fieldMatch.first();
        second = fieldMatch.second();
        message = fieldMatch.message();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        final Object firstObject = BeanUtils.getProperty(object, first);
        final Object secondObject = BeanUtils.getProperty(object, second);
        boolean valid = (firstObject == null && secondObject == null)
                || (firstObject != null && firstObject.equals(secondObject));
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(first).addConstraintViolation();
        }
        return valid;
    }
}
