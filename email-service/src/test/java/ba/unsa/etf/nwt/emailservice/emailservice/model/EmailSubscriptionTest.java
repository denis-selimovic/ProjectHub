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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class EmailSubscriptionTest {

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
    public void testNoTask() {
        EmailSubscription emailSubscription = new EmailSubscription();

        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setEmail("ajsa@projecthub.com");
        emailConfig.setUserId(UUID.randomUUID());

        emailSubscription.setConfig(emailConfig);

        List<ConstraintViolation<EmailSubscription>> violations = new ArrayList<>(validator.validate(emailSubscription));

        assertEquals(1, violations.size());
        assertEquals("Task can't be null", violations.get(0).getMessage());

    }
}
