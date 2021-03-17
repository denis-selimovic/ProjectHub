package ba.unsa.etf.nwt.userservice.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class TokenTest {

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
    public void testTokenWithoutUser() {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setType(Token.TokenType.ACTIVATE_ACCOUNT);
        token.setDuration(10);
        token.setToken(UUID.randomUUID().toString());

        List<String> violations = mapValidationErrors(token);
        assertEquals(violations.size(), 1);
        assertTrue(violations.contains("Token must have a user"));
    }

    @Test
    public void testTokenDefaultValidity() {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setType(Token.TokenType.ACTIVATE_ACCOUNT);
        token.setDuration(10);
        token.setToken(UUID.randomUUID().toString());
        token.setUser(createUser());

        List<String> violations = mapValidationErrors(token);
        assertEquals(violations.size(), 0);
        assertTrue(token.getValid());
    }

    @Test
    public void testTokenNegativeDuration() {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setType(Token.TokenType.RESET_PASSWORD);
        token.setDuration(-2);
        token.setToken(UUID.randomUUID().toString());
        token.setUser(createUser());

        List<String> violations = mapValidationErrors(token);
        assertEquals(violations.size(), 1);
        assertTrue(violations.contains("Duration must be a positive integer"));
    }

    @Test
    public void testWrongTokenSize() {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setType(Token.TokenType.RESET_PASSWORD);
        token.setDuration(20);
        token.setToken("123");
        token.setUser(createUser());

        List<String> violations = mapValidationErrors(token);
        assertEquals(violations.size(), 1);
        assertTrue(violations.contains("Token must have at least 8 characters"));
    }

    @Test
    public void testWhenTypeIsNull() {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setDuration(100);
        token.setToken(UUID.randomUUID().toString());
        token.setUser(createUser());

        List<String> violations = mapValidationErrors(token);
        assertEquals(violations.size(), 1);
        assertTrue(violations.contains("Type can't be null"));
    }

    @Test
    public void testWhenTokenIsNull() {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setType(Token.TokenType.ACTIVATE_ACCOUNT);
        token.setDuration(100);
        token.setUser(createUser());

        List<String> violations = mapValidationErrors(token);
        assertEquals(violations.size(), 1);
        assertTrue(violations.contains("Token can't be null"));
    }

    @Test
    public void testAllValid() {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setType(Token.TokenType.ACTIVATE_ACCOUNT);
        token.setDuration(100);
        token.setToken(UUID.randomUUID().toString());
        token.setUser(createUser());

        List<String> violations = mapValidationErrors(token);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testTokenNotExpired() {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setType(Token.TokenType.ACTIVATE_ACCOUNT);
        token.setDuration(300);
        token.setToken(UUID.randomUUID().toString());
        token.setUser(createUser());
        token.setCreatedAt(Instant.now());

        List<String> violations = mapValidationErrors(token);
        assertTrue(violations.isEmpty());
        assertFalse(token.isExpired());
    }

    @Test
    public void testTokenExpired() {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setType(Token.TokenType.ACTIVATE_ACCOUNT);
        token.setDuration(550);
        token.setToken(UUID.randomUUID().toString());
        token.setUser(createUser());
        token.setCreatedAt(Instant.now().minusSeconds(600));

        List<String> violations = mapValidationErrors(token);
        assertTrue(violations.isEmpty());
        assertTrue(token.isExpired());
    }

    private List<String> mapValidationErrors(Token token) {
        return new ArrayList<>(validator.validate(token))
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

    private User createUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user@user.com");
        user.setPassword("Password!@#$!@123");
        user.setFirstName("User");
        user.setLastName("User");
        return user;
    }
}
