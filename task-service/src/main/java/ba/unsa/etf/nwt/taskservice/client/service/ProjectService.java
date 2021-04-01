package ba.unsa.etf.nwt.taskservice.client.service;

import ba.unsa.etf.nwt.taskservice.client.ProjectServiceClient;
import ba.unsa.etf.nwt.taskservice.client.dto.ProjectDTO;
import ba.unsa.etf.nwt.taskservice.exception.base.UnpocessableEntityException;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectServiceClient projectServiceClient;

    public void checkIfProjectExists(ResourceOwner resourceOwner, final UUID projectId) {
        ResponseEntity<ProjectDTO> response = projectServiceClient
                .getProjectById(resourceOwner.getAuthHeader(), projectId);
        //ovo ne moze ovako, baci se izuzetak, treba ga uhvatiti
        if(response.getStatusCodeValue() != 200){
            throw new UnpocessableEntityException("Project doesn't exist");
        }
    }
}
