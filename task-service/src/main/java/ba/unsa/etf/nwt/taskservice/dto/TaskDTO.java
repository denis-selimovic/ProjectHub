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
    private UUID statusId;
    private UUID typeId;
    private Instant createdAt;
    private Instant updatedAt;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.userId = task.getUserId();
        this.projectId = task.getProjectId();
        this.statusId = task.getStatus().getId();
        this.typeId = task.getType().getId();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }
}
