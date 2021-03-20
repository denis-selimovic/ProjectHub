package ba.unsa.etf.nwt.taskservice.model;

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
public class TaskTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private static Task task;
    private static Priority priority;
    private static Status status;
    private static Type type;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        priority = new Priority();
        priority.setPriorityType(Priority.PriorityType.LOW);

        status = new Status();
        status.setStatus(Status.StatusType.OPEN);

        type = new Type();
        type.setType(Type.TaskType.BUG);
    }

    @BeforeEach
    public void createTask() {
        task = new Task();
        task.setName("Task1");
        task.setDescription("First task");
        task.setUserId(UUID.randomUUID());
        task.setProjectId(UUID.randomUUID());
        task.setPriority(priority);
        task.setStatus(status);
        task.setType(type);
    }

    @Test
    public void testMultipleViolations() {
        Task task = new Task();
        List<String> violations = validator
                .validate(task)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(7, violations.size());
        assertTrue(violations.contains("Task name can't be blank"));
        assertTrue(violations.contains("Task description can't be blank"));
        assertTrue(violations.contains("User id can't be null"));
        assertTrue(violations.contains("Project id can't be null"));
        assertTrue(violations.contains("Priority id can't be null"));
        assertTrue(violations.contains("Status id can't be null"));
        assertTrue(violations.contains("Type id can't be null"));
    }

    @Test
    public void testNoViolations() {
        List<ConstraintViolation<Task>> violations = new ArrayList<>(validator.validate(task));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testBlankName() {
        task.setName("");
        List<ConstraintViolation<Task>> violations = new ArrayList<>(validator.validate(task));
        assertEquals(1, violations.size());
        assertEquals("Task name can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testLongName() {
        task.setName("t".repeat(51));
        List<ConstraintViolation<Task>> violations = new ArrayList<>(validator.validate(task));
        assertEquals(1, violations.size());
        assertEquals("Task name can contain at most 50 characters", violations.get(0).getMessage());
    }

    @Test
    public void testBlankDescription() {
        task.setDescription("");
        List<ConstraintViolation<Task>> violations = new ArrayList<>(validator.validate(task));
        assertEquals(1, violations.size());
        assertEquals("Task description can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testLongDescription() {
        task.setDescription("t".repeat(256));
        List<ConstraintViolation<Task>> violations = new ArrayList<>(validator.validate(task));
        assertEquals(1, violations.size());
        assertEquals("Task description can contain at most 255 characters", violations.get(0).getMessage());
    }

    @Test
    public void testNoUserId() {
        task.setUserId(null);
        List<ConstraintViolation<Task>> violations = new ArrayList<>(validator.validate(task));
        assertEquals(1, violations.size());
        assertEquals("User id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoProjectId() {
        task.setProjectId(null);
        List<ConstraintViolation<Task>> violations = new ArrayList<>(validator.validate(task));
        assertEquals(1, violations.size());
        assertEquals("Project id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoPriorityId() {
        task.setPriority(null);
        List<ConstraintViolation<Task>> violations = new ArrayList<>(validator.validate(task));
        assertEquals(1, violations.size());
        assertEquals("Priority id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoStatusId() {
        task.setStatus(null);
        List<ConstraintViolation<Task>> violations = new ArrayList<>(validator.validate(task));
        assertEquals(1, violations.size());
        assertEquals("Status id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoTypeId() {
        task.setType(null);
        List<ConstraintViolation<Task>> violations = new ArrayList<>(validator.validate(task));
        assertEquals(1, violations.size());
        assertEquals("Type id can't be null", violations.get(0).getMessage());
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }


}
