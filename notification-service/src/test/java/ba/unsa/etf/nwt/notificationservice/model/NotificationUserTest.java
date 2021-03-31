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
public class NotificationUserTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testNoViolations() {
        NotificationUser notificationUser = createNotificationUser(UUID.randomUUID(), UUID.randomUUID());

        List<ConstraintViolation<NotificationUser>> violations = new ArrayList<>(validator.validate(notificationUser));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testMultipleViolations() {
        NotificationUser notificationUser = new NotificationUser();
        List<String> violations = validator
                .validate(notificationUser)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(2, violations.size());
        assertTrue(violations.contains("Notification id can't be null"));
        assertTrue(violations.contains("User id can't be null"));
    }

    @Test
    public void testNoUserId() {
        NotificationUser notificationUser = createNotificationUser(UUID.randomUUID(), null);

        List<ConstraintViolation<NotificationUser>> violations = new ArrayList<>(validator.validate(notificationUser));
        assertEquals(1, violations.size());
        assertEquals("User id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoNotificationId() {
        NotificationUser notificationUser = createNotificationUser( null, UUID.randomUUID());

        List<ConstraintViolation<NotificationUser>> violations = new ArrayList<>(validator.validate(notificationUser));
        assertEquals(1, violations.size());
        assertEquals("Notification id can't be null", violations.get(0).getMessage());
    }

    private NotificationUser createNotificationUser(UUID notificationId, UUID userId) {
        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setUserId(userId);
        notificationUser.setNotificationId(notificationId);

        return notificationUser;
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

}
