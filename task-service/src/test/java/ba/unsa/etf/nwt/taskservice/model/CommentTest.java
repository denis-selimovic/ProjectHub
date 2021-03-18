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
public class CommentTest {
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
        Comment comment = new Comment();
        List<String> violations = validator
                .validate(comment)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(3, violations.size());
        assertTrue(violations.contains("Comment can't be blank"));
        assertTrue(violations.contains("User id can't be null"));
        assertTrue(violations.contains("Task id can't be null"));
    }

    @Test
    public void testNoViolations() {
        Comment comment = new Comment();
        comment.setText("This is a comment.");
        comment.setUser_id(UUID.randomUUID());
        comment.setTask(createTask());

        List<ConstraintViolation<Comment>> violations = new ArrayList<>(validator.validate(comment));
        assertTrue(violations.isEmpty());
    }


    @Test
    public void testBlankComment() {
        Comment comment = new Comment();
        comment.setText("");
        comment.setUser_id(UUID.randomUUID());
        comment.setTask(createTask());

        List<ConstraintViolation<Comment>> violations = new ArrayList<>(validator.validate(comment));
        assertEquals(1, violations.size());
        assertEquals("Comment can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testLongComment() {
        Comment comment = new Comment();
        comment.setText("a".repeat(256));
        comment.setUser_id(UUID.randomUUID());
        comment.setTask(createTask());

        List<ConstraintViolation<Comment>> violations = new ArrayList<>(validator.validate(comment));
        assertEquals(1, violations.size());
        assertEquals("Comment can contain at most 255 characters", violations.get(0).getMessage());
    }

    @Test
    public void testNoUser() {
        Comment comment = new Comment();
        comment.setText("This is a comment.");
        comment.setTask(createTask());

        List<ConstraintViolation<Comment>> violations = new ArrayList<>(validator.validate(comment));
        assertEquals(1, violations.size());
        assertEquals("User id can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testNoTask() {
        Comment comment = new Comment();
        comment.setText("This is a comment.");
        comment.setUser_id(UUID.randomUUID());

        List<ConstraintViolation<Comment>> violations = new ArrayList<>(validator.validate(comment));
        assertEquals(1, violations.size());
        assertEquals("Task id can't be null", violations.get(0).getMessage());
    }

    public static Task createTask() {
        Task task = new Task();
        task.setName("Task name");
        task.setDescription("Task description");
        task.setUser_id(UUID.randomUUID());
        task.setProject_id(UUID.randomUUID());

        Priority priority = new Priority();
        priority.setPriorityType(Priority.PriorityType.CRITICAL);

        Status status = new Status();
        status.setStatus(Status.StatusType.IN_PROGRESS);

        Type type = new Type();
        type.setType(Type.TaskType.BUG);

        task.setPriority(priority);
        task.setStatus(status);
        task.setType(type);

        return task;
    }
}
