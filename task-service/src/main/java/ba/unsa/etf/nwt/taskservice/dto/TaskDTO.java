package ba.unsa.etf.nwt.taskservice.dto;

import ba.unsa.etf.nwt.taskservice.model.Task;
import ba.unsa.etf.nwt.taskservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO implements Resource {
    private UUID id;
    private String name;
    private String description;
    private UUID userId;
    private UUID projectId;
    private PriorityDTO priority;
    private StatusDTO status;
    private TypeDTO type;
    private Instant createdAt;
    private Instant updatedAt;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.userId = task.getUserId();
        this.projectId = task.getProjectId();
        this.priority = new PriorityDTO(task.getPriority());
        this.status = new StatusDTO(task.getStatus());
        this.type = new TypeDTO(task.getType());
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }
}
