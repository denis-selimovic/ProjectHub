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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
public class NotificationTest {
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
        Notification notification = new Notification();
        notification.setTitle("Notification title");
        notification.setDescription("Notification description");

        List<ConstraintViolation<Notification>> violations = new ArrayList<>(validator.validate(notification));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testLongTitle() {
        Notification notification = new Notification();
        notification.setTitle("a".repeat(51));
        notification.setDescription("Notification description");

        List<ConstraintViolation<Notification>> violations = new ArrayList<>(validator.validate(notification));
        assertEquals(1, violations.size());
        assertEquals("Notification title can have at most 50 characters", violations.get(0).getMessage());
    }

    @Test
    public void testBlankName() {
        Notification notification = new Notification();
        notification.setTitle("");
        notification.setDescription("Notification description");

        List<ConstraintViolation<Notification>> violations = new ArrayList<>(validator.validate(notification));
        assertEquals(1, violations.size());
        assertEquals("Notification title can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testNoName() {
        Notification notification = new Notification();
        notification.setDescription("Notification description");

        List<ConstraintViolation<Notification>> violations = new ArrayList<>(validator.validate(notification));
        assertEquals(1, violations.size());
        assertEquals("Notification title can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testTooLongDescription() {
        Notification notification = new Notification();
        notification.setTitle("Notification title");
        notification.setDescription("a".repeat(201));

        List<ConstraintViolation<Notification>> violations = new ArrayList<>(validator.validate(notification));
        assertEquals(1, violations.size());
        assertEquals("Notification description can have at most 200 characters", violations.get(0).getMessage());
    }

    @Test
    public void testMultipleViolations() {
        Notification notification = new Notification();
        List<String> violations = validator
                .validate(notification)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(2, violations.size());
        assertTrue(violations.contains("Notification title can't be blank"));
        assertTrue(violations.contains("Notification description can't be blank"));
    }
}
