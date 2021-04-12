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
public class SubscriptionConfigTest {
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
        SubscriptionConfig subscriptionConfig = new SubscriptionConfig();
        subscriptionConfig.setEmail("lv@gmail.com");
        subscriptionConfig.setUserId(UUID.randomUUID());

        List<ConstraintViolation<SubscriptionConfig>> violations = new ArrayList<>(validator.validate(subscriptionConfig));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testMultipleViolations() {
        SubscriptionConfig config = new SubscriptionConfig();
        List<String> violations = validator
                .validate(config)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(2, violations.size());
        assertTrue(violations.contains("User id can't be null"));
        assertTrue(violations.contains("Email can't be blank"));
    }

    @Test
    public void testBlankEmail() {
        SubscriptionConfig config = new SubscriptionConfig();
        config.setEmail("");
        config.setUserId(UUID.randomUUID());

        List<String> violations = validator
                .validate(config)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(1, violations.size());
        assertTrue(violations.contains("Email can't be blank"));
    }

    @Test
    public void testEmailTooLong() {
        SubscriptionConfig config = new SubscriptionConfig();
        config.setEmail("a".repeat(60));
        config.setUserId(UUID.randomUUID());

        List<String> violations = validator
                .validate(config)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(1, violations.size());
        assertTrue(violations.contains("Email can't contain more than 50 characters"));
    }
}
