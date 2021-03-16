package ba.unsa.etf.nwt.projectservice.projectservice.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectTest {
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
    public void testBlankName() {
        Project project = new Project();
        project.setName("");
        project.setOwnerId(UUID.randomUUID());

        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertEquals(1, violations.size());
    }

    @Test
    public void testNoName() {
        Project project = new Project();
        project.setOwnerId(UUID.randomUUID());

        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertEquals(1, violations.size());
    }
}
