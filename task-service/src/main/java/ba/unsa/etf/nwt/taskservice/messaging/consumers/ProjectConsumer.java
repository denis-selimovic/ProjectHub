package ba.unsa.etf.nwt.taskservice.messaging.consumers;

import ba.unsa.etf.nwt.taskservice.dto.ProjectDTO;
import ba.unsa.etf.nwt.taskservice.messaging.Consumer;
import ba.unsa.etf.nwt.taskservice.messaging.publishers.ProjectPublisher;
import ba.unsa.etf.nwt.taskservice.service.IssueService;
import ba.unsa.etf.nwt.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProjectConsumer implements Consumer<ProjectDTO> {

    private final TaskService taskService;
    private final IssueService issueService;
    private final ProjectPublisher publisher;

    @RabbitListener(queues = "delete-project-queue")
    public void receive(ProjectDTO data) {
        try {
            deleteTasksAndIssues(data.getId());
        } catch (Exception e) {
            publisher.send(data);
        }
    }

    @Transactional
    public void deleteTasksAndIssues(UUID projectId) {
        taskService.deleteTasksOnProject(projectId);
        issueService.deleteIssuesOnProject(projectId);
    }
}
