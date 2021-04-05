package ba.unsa.etf.nwt.taskservice.client.service;

import ba.unsa.etf.nwt.taskservice.client.ProjectServiceClient;
import ba.unsa.etf.nwt.taskservice.client.dto.ProjectCollaboratorDTO;
import ba.unsa.etf.nwt.taskservice.client.dto.ProjectDTO;
import ba.unsa.etf.nwt.taskservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.taskservice.response.base.Response;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectServiceClient projectServiceClient;

    public ProjectDTO findProjectById(ResourceOwner resourceOwner, final UUID projectId) {
        System.out.println("Response " + projectServiceClient
                .getProjectById(resourceOwner.getAuthHeader(), projectId)
                .getBody()
                .getData());
       return Objects.requireNonNull(projectServiceClient
               .getProjectById(resourceOwner.getAuthHeader(), projectId)
               .getBody())
               .getData();
    }

    public ProjectCollaboratorDTO findCollaboratorById(ResourceOwner resourceOwner, final UUID projectId, final UUID collaboratorId) {
        System.out.println("Ovaj drugi " + projectServiceClient
                .getCollaboratorById(resourceOwner.getAuthHeader(), projectId, collaboratorId)
                .getBody());
        return Objects.requireNonNull(projectServiceClient
                .getCollaboratorById(resourceOwner.getAuthHeader(), projectId, collaboratorId)
                .getBody())
                .getData();
    }

    public ProjectDTO findProjectByIdAndOwner(ResourceOwner resourceOwner, final UUID projectId) {
        ProjectDTO projectDTO = findProjectById(resourceOwner, projectId);
        if(!projectDTO.getOwnerId().equals(resourceOwner.getId()))
            throw new ForbiddenException("You don't have permission for this activity");
        return projectDTO;
    }

    public void checkIfOwnerOrCollaborator(ResourceOwner resourceOwner, ProjectDTO projectDTO) {
        if (!projectDTO.getOwnerId().equals(resourceOwner.getId()))
            projectServiceClient
                    .getCollaboratorById(resourceOwner.getAuthHeader(), projectDTO.getId(), resourceOwner.getId())
                    .getBody();
    }
}
