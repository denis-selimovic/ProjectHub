package ba.unsa.etf.nwt.projectservice.projectservice.model;

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

        List<ConstraintViolation<Project>> violations = new ArrayList<>(validator.validate(project));
        assertEquals(1, violations.size());
        assertEquals("Project name can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testNoName() {
        Project project = new Project();
        project.setOwnerId(UUID.randomUUID());

        List<ConstraintViolation<Project>> violations = new ArrayList<>(validator.validate(project));
        assertEquals(1, violations.size());
        assertEquals("Project name can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testNoOwner() {
        Project project = new Project();
        project.setName("Ime projekta");

        List<ConstraintViolation<Project>> violations = new ArrayList<>(validator.validate(project));
        assertEquals(1, violations.size());
        assertEquals("Project must have an owner", violations.get(0).getMessage());
    }

    @Test
    public void testMultipleViolations() {
        Project project = new Project();
        List<String> violations = validator
                .validate(project)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(2, violations.size());
        assertTrue(violations.contains("Project must have an owner"));
        assertTrue(violations.contains("Project name can't be blank"));
    }
}
