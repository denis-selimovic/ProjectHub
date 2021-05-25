package ba.unsa.etf.nwt.projectservice.projectservice.dto;

import ba.unsa.etf.nwt.projectservice.projectservice.client.dto.UserDTO;
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
public class ProjectCollaboratorDTO implements Resource {
    private UUID id;
    private ProjectDTO project;
    private UUID collaboratorId;
    private UserDTO collaborator;

    public ProjectCollaboratorDTO(ProjectCollaborator projectCollaborator) {
        id = projectCollaborator.getId();
        project = new ProjectDTO(projectCollaborator.getProject());
        collaboratorId = projectCollaborator.getCollaboratorId();
    }
}
