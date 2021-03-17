package ba.unsa.etf.nwt.emailservice.emailservice.model;

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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
public class EmailSubscriptionTest {

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
    public void testNoTask() {
        EmailSubscription emailSubscription = new EmailSubscription();

        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setEmail("ajsa@projecthub.com");
        emailConfig.setUserId(UUID.randomUUID());

        emailSubscription.setConfig(emailConfig);

        List<ConstraintViolation<EmailSubscription>> violations = new ArrayList<>(validator.validate(emailSubscription));
        assertEquals(1, violations.size());
        assertEquals("Task id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoConfig() {
        EmailSubscription emailSubscription = new EmailSubscription();
        emailSubscription.setTask(UUID.randomUUID());

        List<ConstraintViolation<EmailSubscription>> violations = new ArrayList<>(validator.validate(emailSubscription));
        assertEquals(1, violations.size());
        assertEquals("Email configuration can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testMultipleViolations() {
        EmailSubscription emailSubscription = new EmailSubscription();

        List<String> violations = validator
                .validate(emailSubscription)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(2, violations.size());
        assertTrue(violations.contains("Email configuration can't be null"));
        assertTrue(violations.contains("Task id can't be null"));
    }

    @Test
    public void testNoViolations() {
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setEmail("ajsa@projecthub.com");
        emailConfig.setUserId(UUID.randomUUID());

        EmailSubscription emailSubscription = new EmailSubscription();
        emailSubscription.setTask(UUID.randomUUID());
        emailSubscription.setConfig(emailConfig);

        Set<ConstraintViolation<EmailSubscription>> violations = validator.validate(emailSubscription);
        assertTrue(violations.isEmpty());
    }
}
