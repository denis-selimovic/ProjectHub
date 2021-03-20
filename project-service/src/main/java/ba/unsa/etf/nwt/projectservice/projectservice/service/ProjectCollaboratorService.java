package ba.unsa.etf.nwt.projectservice.projectservice.service;

import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectCollaboratorRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.request.AddCollaboratorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectCollaboratorService {
    private final ProjectCollaboratorRepository projectCollaboratorRepository;


    public ProjectCollaborator createCollaborator(AddCollaboratorRequest request, Project project) {
        projectCollaboratorRepository.findByProject(project).ifPresent(pc -> {
            throw new UnprocessableEntityException("Request body can not be processed. This collaborator is already added");
        });

        ProjectCollaborator projectCollaborator = new ProjectCollaborator();
        projectCollaborator.setProject(project);
        projectCollaborator.setCollaboratorId(request.getCollaboratorId());
        return projectCollaboratorRepository.save(projectCollaborator);
    }

    public boolean existsById(UUID collaboratorId) {
        return projectCollaboratorRepository.existsById(collaboratorId);
    }
}
