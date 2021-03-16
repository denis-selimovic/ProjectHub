package ba.unsa.etf.nwt.userservice.model;

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
public class UserTest {

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
    public void testInvalidEmail() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("   ");
        user.setPassword("password123!.Password123!");
        user.setFirstName("User");
        user.setLastName("User");

        List<String> violations = new ArrayList<>(validator.validate(user))
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        assertEquals(violations.size(), 2);
        assertTrue(violations.contains("Email can't be blank"));
        assertTrue(violations.contains("Invalid email format"));
    }

    @Test
    public void testInvalidBlankPassword() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user@user.com");
        user.setPassword("  ");
        user.setFirstName("User");
        user.setLastName("User");

        List<String> violations = new ArrayList<>(validator.validate(user))
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        assertEquals(violations.size(), 3);
        assertTrue(violations.contains("Password can't be blank"));
        assertTrue(violations.contains("Password must contain at least eight characters"));
        assertTrue(violations.contains("Password must contain at least one lowercase, one uppercase, one digit and one special character"));
    }

    @Test
    public void testInvalidPasswordSize() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user@user.com");
        user.setPassword("123");
        user.setFirstName("User");
        user.setLastName("User");

        List<String> violations = new ArrayList<>(validator.validate(user))
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        assertEquals(violations.size(), 2);
        assertTrue(violations.contains("Password must contain at least eight characters"));
        assertTrue(violations.contains("Password must contain at least one lowercase, one uppercase, one digit and one special character"));
    }

    @Test
    public void testBlankFirstAndLastName() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user@user.com");
        user.setPassword("password123.Password");
        user.setFirstName("   ");
        user.setLastName(" ");

        List<String> violations = new ArrayList<>(validator.validate(user))
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        assertEquals(violations.size(), 2);
        assertTrue(violations.contains("First name can't be blank"));
        assertTrue(violations.contains("Last name can't be blank"));
    }
}
