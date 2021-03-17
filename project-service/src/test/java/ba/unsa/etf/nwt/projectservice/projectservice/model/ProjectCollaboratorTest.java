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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
public class ProjectCollaboratorTest {
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
    public void testProjectNull() {
        ProjectCollaborator projectCollaborator = new ProjectCollaborator();
        projectCollaborator.setCollaboratorId(UUID.randomUUID());

        List<ConstraintViolation<ProjectCollaborator>> violations = new ArrayList<>(validator.validate(projectCollaborator));
        assertEquals(1, violations.size());
        assertEquals("Project can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testCollaboratorNull() {
        Project project = new Project();
        project.setOwnerId(UUID.randomUUID());
        project.setName("Ime projekta");
        ProjectCollaborator projectCollaborator = new ProjectCollaborator();

        projectCollaborator.setProject(project);

        List<ConstraintViolation<ProjectCollaborator>> violations = new ArrayList<>(validator.validate(projectCollaborator));
        assertEquals(1, violations.size());
        assertEquals("Collaborator id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoViolations() {
        Project project = new Project();
        project.setOwnerId(UUID.randomUUID());
        project.setName("Ime projekta");
        ProjectCollaborator projectCollaborator = new ProjectCollaborator();

        projectCollaborator.setProject(project);
        projectCollaborator.setCollaboratorId(UUID.randomUUID());

        Set<ConstraintViolation<ProjectCollaborator>> violations = validator.validate(projectCollaborator);
        assertTrue( violations.isEmpty());
    }

    @Test
    public void testMultipleViolations() {
        ProjectCollaborator projectCollaborator= new ProjectCollaborator();
        List<String> violations = validator
                .validate(projectCollaborator)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(2, violations.size());
        assertTrue(violations.contains("Project can't be null"));
        assertTrue(violations.contains("Collaborator id can't be null"));
    }
}
