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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class CommentTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    Optional<Priority> criticalPriority, highPriority, mediumPriority, lowPriority;
    Optional<Status> open, inProgress, inReview, inTesting, done;
    Optional<Type> spike, bug, epic, story, change;

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
    public void testLongComment() {
        Task task = new Task();
        task.setName("Task name");
        task.setDescription("Task description");
        task.setUser_id(UUID.randomUUID());
        task.setProject_id(UUID.randomUUID());
        task.setPriority(criticalPriority.get());
        task.setStatus(open.get());
        task.setType(bug.get());

        Comment comment = new Comment();
        comment.setText("a".repeat(256));
        comment.setUser_id(UUID.randomUUID());
        comment.setTask(task);

        List<ConstraintViolation<Comment>> violations = new ArrayList<>(validator.validate(comment));
        assertEquals(1, violations.size());
        assertEquals("Comment can contain at most 255 characters", violations.get(0).getMessage());
    }

}
