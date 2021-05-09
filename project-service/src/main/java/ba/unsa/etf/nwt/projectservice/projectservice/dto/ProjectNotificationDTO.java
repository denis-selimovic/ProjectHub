package ba.unsa.etf.nwt.projectservice.projectservice.dto;

import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectNotificationDTO implements Resource {

    private String title;
    private String description;

    public ProjectNotificationDTO(Project project) {
        this.title = project.getName();
        this.description = String.format("You were added to %s", project.getName());
    }
}
