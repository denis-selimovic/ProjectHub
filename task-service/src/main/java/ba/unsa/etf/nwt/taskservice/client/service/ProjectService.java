package ba.unsa.etf.nwt.taskservice.client.service;

import ba.unsa.etf.nwt.taskservice.client.ProjectServiceClient;
import ba.unsa.etf.nwt.taskservice.client.utility.Utility;
import ba.unsa.etf.nwt.taskservice.client.dto.ProjectDTO;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectServiceClient projectServiceClient;

    public ProjectDTO findProjectById(ResourceOwner resourceOwner, final UUID projectId) {
       return projectServiceClient
                .getProjectById(Utility.getAuthHeader(resourceOwner), projectId)
               .getBody();
    }
}
