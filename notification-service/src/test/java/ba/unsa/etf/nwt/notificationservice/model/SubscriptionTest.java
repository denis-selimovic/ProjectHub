package ba.unsa.etf.nwt.notificationservice.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
public class SubscriptionTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void testNoViolations() {
        Subscription subscription = new Subscription();
        subscription.setUserId(UUID.randomUUID());
        subscription.setTaskId(UUID.randomUUID());

        List<ConstraintViolation<Subscription>> violations = new ArrayList<>(validator.validate(subscription));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testMultipleViolations() {
        Subscription subscription = new Subscription();
        List<String> violations = validator
                .validate(subscription)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(2, violations.size());
        assertTrue(violations.contains("Task id can't be null"));
        assertTrue(violations.contains("User id can't be null"));
    }

    @Test
    public void testNoUser() {
        Subscription subscription = new Subscription();
        subscription.setTaskId(UUID.randomUUID());

        List<ConstraintViolation<Subscription>> violations = new ArrayList<>(validator.validate(subscription));
        assertEquals(1, violations.size());
        assertEquals("User id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoTask() {
        Subscription subscription = new Subscription();
        subscription.setUserId(UUID.randomUUID());

        List<ConstraintViolation<Subscription>> violations = new ArrayList<>(validator.validate(subscription));
        assertEquals(1, violations.size());
        assertEquals("Task id can't be null", violations.get(0).getMessage());
    }
}
