package ba.unsa.etf.nwt.taskservice.dto;

import ba.unsa.etf.nwt.taskservice.model.Issue;
import ba.unsa.etf.nwt.taskservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO implements Resource {
    private UUID id;
    private String name;
    private String description;
    private UUID projectId;
    private UUID priorityId;
    private Instant createdAt;
    private Instant updatedAt;

    public IssueDTO(final Issue issue) {
        this.id = issue.getId();
        this.name = issue.getName();
        this.description = issue.getDescription();
        this.projectId = issue.getProjectId();
        this.priorityId = issue.getPriority().getId();
        this.createdAt = issue.getCreatedAt();
        this.updatedAt = issue.getUpdatedAt();
    }
}
