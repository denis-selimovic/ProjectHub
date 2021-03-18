package ba.unsa.etf.nwt.taskservice.model;

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
public class IssueTest {
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
    public void testMultipleViolations() {
        Issue issue = new Issue();
        List<String> violations = validator
                .validate(issue)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(4, violations.size());
        assertTrue(violations.contains("Issue name can't be blank"));
        assertTrue(violations.contains("Issue description can't be blank"));
        assertTrue(violations.contains("Project id can't be null"));
        assertTrue(violations.contains("Priority id can't be null"));
    }

    @Test
    public void testNoViolations() {
        Priority priority = new Priority();
        priority.setPriorityType(Priority.PriorityType.CRITICAL);

        Issue issue = new Issue();
        issue.setName("Issue 1");
        issue.setDescription("Issue description");
        issue.setProject_id(UUID.randomUUID());
        issue.setPriority(priority);
        List<ConstraintViolation<Issue>> violations = new ArrayList<>(validator.validate(issue));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testBlankName() {
        Priority priority = new Priority();
        priority.setPriorityType(Priority.PriorityType.CRITICAL);

        Issue issue = new Issue();
        issue.setName("");
        issue.setDescription("Issue description");
        issue.setProject_id(UUID.randomUUID());
        issue.setPriority(priority);

        List<ConstraintViolation<Issue>> violations = new ArrayList<>(validator.validate(issue));
        assertEquals(1, violations.size());
        assertEquals("Issue name can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testLongName() {
        Priority priority = new Priority();
        priority.setPriorityType(Priority.PriorityType.CRITICAL);

        Issue issue = new Issue();
        issue.setName("a".repeat(60));
        issue.setDescription("Issue description");
        issue.setProject_id(UUID.randomUUID());
        issue.setPriority(priority);

        List<ConstraintViolation<Issue>> violations = new ArrayList<>(validator.validate(issue));
        assertEquals(1, violations.size());
        assertEquals("Issue name can contain at most 50 characters", violations.get(0).getMessage());
    }

    @Test
    public void testBlankDescription() {
        Priority priority = new Priority();
        priority.setPriorityType(Priority.PriorityType.CRITICAL);

        Issue issue = new Issue();
        issue.setName("Issue 1");
        issue.setDescription("");
        issue.setProject_id(UUID.randomUUID());
        issue.setPriority(priority);

        List<ConstraintViolation<Issue>> violations = new ArrayList<>(validator.validate(issue));
        assertEquals(1, violations.size());
        assertEquals("Issue description can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testLongDescription() {
        Priority priority = new Priority();
        priority.setPriorityType(Priority.PriorityType.CRITICAL);

        Issue issue = new Issue();
        issue.setName("Issue name");
        issue.setDescription("a".repeat(260));
        issue.setProject_id(UUID.randomUUID());
        issue.setPriority(priority);

        List<ConstraintViolation<Issue>> violations = new ArrayList<>(validator.validate(issue));
        assertEquals(1, violations.size());
        assertEquals("Issue description can contain at most 255 characters", violations.get(0).getMessage());
    }

    @Test
    public void testNoProject() {
        Priority priority = new Priority();
        priority.setPriorityType(Priority.PriorityType.CRITICAL);

        Issue issue = new Issue();
        issue.setName("Issue name");
        issue.setDescription("Issue description");
        issue.setPriority(priority);

        List<ConstraintViolation<Issue>> violations = new ArrayList<>(validator.validate(issue));
        assertEquals(1, violations.size());
        assertEquals("Project id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoPriority() {
        Priority priority = new Priority();
        priority.setPriorityType(Priority.PriorityType.CRITICAL);

        Issue issue = new Issue();
        issue.setName("Issue name");
        issue.setDescription("Issue description");
        issue.setProject_id(UUID.randomUUID());

        List<ConstraintViolation<Issue>> violations = new ArrayList<>(validator.validate(issue));
        assertEquals(1, violations.size());
        assertEquals("Priority id can't be null", violations.get(0).getMessage());
    }
}
