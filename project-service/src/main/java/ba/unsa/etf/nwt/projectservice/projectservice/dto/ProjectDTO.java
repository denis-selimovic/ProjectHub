package ba.unsa.etf.nwt.projectservice.projectservice.dto;

import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO implements Resource {

    private UUID id;
    private String name;
    private UUID ownerId;
    private Instant createdAt;
    private Instant updatedAt;

    public ProjectDTO(Project project) {
        id = project.getId();
        name = project.getName();
        ownerId = project.getOwnerId();
        createdAt = project.getCreatedAt();
        updatedAt = project.getUpdatedAt();
    }
}
