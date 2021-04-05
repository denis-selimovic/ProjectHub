package ba.unsa.etf.nwt.notificationservice.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
public class NotificationUserTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    private static NotificationUser notificationUser;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @BeforeEach
    public void createNotificationUser() {
        Notification notification = new Notification();
        notification.setTitle("Title");
        notification.setDescription("Description");

        notificationUser = new NotificationUser();
        notificationUser.setNotification(notification);
        notificationUser.setUserId(UUID.randomUUID());
        notificationUser.setRead(false);
    }

    @Test
    public void testNoViolations() {
        List<ConstraintViolation<NotificationUser>> violations = new ArrayList<>(validator.validate(notificationUser));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testMultipleViolations() {
        notificationUser.setNotification(null);
        notificationUser.setUserId(null);
        notificationUser.setRead(null);

        List<String> violations = validator
                .validate(notificationUser)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(3, violations.size());
        assertTrue(violations.contains("Notification id can't be null"));
        assertTrue(violations.contains("User id can't be null"));
        assertTrue(violations.contains("Attribute read can't be null"));
    }

    @Test
    public void testNoUserId() {
        notificationUser.setUserId(null);

        List<ConstraintViolation<NotificationUser>> violations = new ArrayList<>(validator.validate(notificationUser));
        assertEquals(1, violations.size());
        assertEquals("User id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoNotificationId() {
        notificationUser.setNotification(null);

        List<ConstraintViolation<NotificationUser>> violations = new ArrayList<>(validator.validate(notificationUser));
        assertEquals(1, violations.size());
        assertEquals("Notification id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoReadValue() {
        notificationUser.setRead(null);

        List<ConstraintViolation<NotificationUser>> violations = new ArrayList<>(validator.validate(notificationUser));
        assertEquals(1, violations.size());
        assertEquals("Attribute read can't be null", violations.get(0).getMessage());
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

}
