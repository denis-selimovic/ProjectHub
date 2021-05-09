package ba.unsa.etf.nwt.projectservice.projectservice.dto;

import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectNotificationDTO implements Resource {

    private String title;
    private String description;
    private UUID userId;

    public ProjectNotificationDTO(ProjectCollaborator projectCollaborator) {
        Project project = projectCollaborator.getProject();
        this.title = project.getName();
        this.description = String.format("You were added to %s", project.getName());
        this.userId = projectCollaborator.getCollaboratorId();
    }
}
