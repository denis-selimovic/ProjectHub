package ba.unsa.etf.nwt.taskservice.seed;

import ba.unsa.etf.nwt.taskservice.model.*;
import ba.unsa.etf.nwt.taskservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static ba.unsa.etf.nwt.taskservice.model.Priority.PriorityType.*;
import static ba.unsa.etf.nwt.taskservice.model.Status.StatusType.*;
import static ba.unsa.etf.nwt.taskservice.model.Type.TaskType.*;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final TaskRepository taskRepository;
    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;
    private final TypeRepository typeRepository;

    @EventListener
    public void seed(final ContextRefreshedEvent event) {
        if(issueRepository.count() == 0)
            seedIssuesTable();
        if(taskRepository.count() == 0)
            seedTasksTable();
    }

    private void seedIssuesTable() {
        Priority criticalPriority = priorityRepository.findByPriorityType(CRITICAL).get(0);
        Priority highPriority = priorityRepository.findByPriorityType(HIGH).get(0);
        Priority mediumPriority = priorityRepository.findByPriorityType(MEDIUM).get(0);
        Priority lowPriority = priorityRepository.findByPriorityType(LOW).get(0);

        Issue i1 = createIssue("Prvo ime", "Prvi opis", UUID.randomUUID(), criticalPriority);
        Issue i2 = createIssue("Drugo ime", "Drugi opis", UUID.randomUUID(), highPriority);
        Issue i3 = createIssue("Trece ime", "Treci opis", UUID.randomUUID(), mediumPriority);
        Issue i4 = createIssue("Cetvrto ime", "Cetvrti opis", UUID.randomUUID(), lowPriority);
        Issue i5 = createIssue("Peto ime", "Peti opis", UUID.randomUUID(), criticalPriority);
        Issue i6 = createIssue("Sesto ime", "Sesti opis", UUID.randomUUID(), highPriority);
        Issue i7 = createIssue("Sedmo ime", "Sedmi opis", UUID.randomUUID(), mediumPriority);
        Issue i8 = createIssue("Osmo ime", "Osmi opis", UUID.randomUUID(), lowPriority);

        issueRepository.save(i1);
        issueRepository.save(i2);
        issueRepository.save(i3);
        issueRepository.save(i4);
        issueRepository.save(i5);
        issueRepository.save(i6);
        issueRepository.save(i7);
        issueRepository.save(i8);
    }

    private void seedTasksTable() {
        Priority criticalPriority = priorityRepository.findByPriorityType(CRITICAL).get(0);
        Priority highPriority = priorityRepository.findByPriorityType(HIGH).get(0);
        Priority mediumPriority = priorityRepository.findByPriorityType(MEDIUM).get(0);
        Priority lowPriority = priorityRepository.findByPriorityType(LOW).get(0);

        Status open = statusRepository.findByStatus(OPEN).get(0);
        Status inProgress = statusRepository.findByStatus(IN_PROGRESS).get(0);
        Status inReview = statusRepository.findByStatus(IN_REVIEW).get(0);
        Status inTesting = statusRepository.findByStatus(IN_TESTING).get(0);
        Status done = statusRepository.findByStatus(DONE).get(0);

        Type spike = typeRepository.findByType(SPIKE).get(0);
        Type bug = typeRepository.findByType(BUG).get(0);
        Type epic = typeRepository.findByType(EPIC).get(0);
        Type story = typeRepository.findByType(STORY).get(0);
        Type change = typeRepository.findByType(CHANGE).get(0);

        Task t1 = createTask("Task1", "Prvi task", UUID.randomUUID(), UUID.randomUUID(), criticalPriority, open, spike);
        Task t2 = createTask("Task2", "Drugi task", UUID.randomUUID(), UUID.randomUUID(), highPriority, inProgress, bug);
        Task t3 = createTask("Task3", "Treci task", UUID.randomUUID(), UUID.randomUUID(), mediumPriority, inReview, epic);
        Task t4 = createTask("Task4", "Cetvrti task", UUID.randomUUID(), UUID.randomUUID(), lowPriority, inTesting, story);
        Task t5 = createTask("Task5", "Peti task", UUID.randomUUID(), UUID.randomUUID(), criticalPriority, done, change);

        taskRepository.save(t1);
        taskRepository.save(t2);
        taskRepository.save(t3);
        taskRepository.save(t4);
        taskRepository.save(t5);

        seedCommentsTable(List.of(t1, t2, t3, t4, t5));
    }

    private void seedCommentsTable(List<Task> tasks) {
        Comment c1 = createComment("Prvi komentar", UUID.randomUUID(), tasks.get(0));
        Comment c2 = createComment("Drugi komentar", UUID.randomUUID(), tasks.get(1));
        Comment c3 = createComment("Treci komentar", UUID.randomUUID(), tasks.get(2));
        Comment c4 = createComment("Cetvrti komentar", UUID.randomUUID(), tasks.get(3));
        Comment c5 = createComment("Peti komentar", UUID.randomUUID(), tasks.get(4));

        commentRepository.save(c1);
        commentRepository.save(c2);
        commentRepository.save(c3);
        commentRepository.save(c4);
        commentRepository.save(c5);
    }

    private Issue createIssue(String name, String description, UUID project_id, Priority priority) {
        Issue i = new Issue();
        i.setName(name);
        i.setDescription(description);
        i.setProject_id(project_id);
        i.setPriority(priority);
        return i;
    }

    private Task createTask(String name, String description, UUID user_id, UUID project_id, Priority priority, Status status, Type type) {
        Task t = new Task();
        t.setName(name);
        t.setDescription(description);
        t.setUser_id(user_id);
        t.setProject_id(project_id);
        t.setPriority(priority);
        t.setStatus(status);
        t.setType(type);
        return t;
    }

    private Comment createComment(String text, UUID user_id, Task task) {
        Comment c = new Comment();
        c.setText(text);
        c.setUser_id(user_id);
        c.setTask(task);
        return c;
    }


}
