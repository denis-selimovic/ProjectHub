package ba.unsa.etf.nwt.emailservice.emailservice.model;

import ba.unsa.etf.nwt.emailservice.emailservice.repository.EmailConfigRepository;
import ba.unsa.etf.nwt.emailservice.emailservice.repository.EmailSubscriptionRepository;
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
public class EmailConfigTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private static EmailConfigRepository emailConfigRepository;
    private static EmailSubscriptionRepository emailSubscriptionRepository;

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
    public void testBlankEmail() {
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setEmail("");
        emailConfig.setUserId(UUID.randomUUID());

        List<ConstraintViolation<EmailConfig>> violations = new ArrayList<>(validator.validate(emailConfig));
        assertEquals(1, violations.size());
        assertEquals("Email can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testNoTask() {
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setEmail("ajsa@projecthub.com");

        List<ConstraintViolation<EmailConfig>> violations = new ArrayList<>(validator.validate(emailConfig));
        assertEquals(1, violations.size());
        assertEquals("User can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testInvalidEmail() {
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setEmail("a@");
        emailConfig.setUserId(UUID.randomUUID());

        List<ConstraintViolation<EmailConfig>> violations = new ArrayList<>(validator.validate(emailConfig));

        assertEquals(1, violations.size());
        assertEquals("Email is invalid", violations.get(0).getMessage());
    }

    @Test
    public void testMultipleViolations() {
        EmailConfig emailConfig = new EmailConfig();
        List<String> violations = validator
                .validate(emailConfig)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(2, violations.size());
        assertTrue(violations.contains("User can't be null"));
        assertTrue(violations.contains("Email can't be blank"));
    }

//    @Test
//    public void delete() {
//        Page<EmailConfig> emailConfigs = emailConfigRepository.findAll(PageRequest.of(0, 3));
//        Page<EmailSubscription> emailSubscriptions = emailSubscriptionRepository.findAll(PageRequest.of(0, 3));
//        EmailConfig emailConfig = emailConfigs.toList().get(0);
//        EmailSubscription toDelete = emailSubscriptions.toList().get(0);
//        emailConfig.getEmailSubscriptions().remove(toDelete);
//
//        Assert.isNull();
//    }
}
