package ba.unsa.etf.nwt.projectservice.projectservice.service;

import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectCollaboratorRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.request.AddCollaboratorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectCollaboratorService {
    private final ProjectCollaboratorRepository projectCollaboratorRepository;


    public ProjectCollaborator createCollaborator(UUID collaboratorId, Project project) {
        projectCollaboratorRepository.findByProject(project).ifPresent(pc -> {
            throw new UnprocessableEntityException("Request body can not be processed.");
        });

        ProjectCollaborator projectCollaborator = new ProjectCollaborator();
        projectCollaborator.setProject(project);
        projectCollaborator.setCollaboratorId(collaboratorId);
        return projectCollaboratorRepository.save(projectCollaborator);
    }

    public ProjectCollaborator findById(UUID collaboratorId) {
        Optional<ProjectCollaborator> projectCollaborator = projectCollaboratorRepository.findById(collaboratorId);
        if (projectCollaborator.isEmpty())
            throw new UnprocessableEntityException("Request body can not be processed.");
        return projectCollaborator.get();
    }

    public void delete(ProjectCollaborator projectCollaborator) {
        projectCollaboratorRepository.delete(projectCollaborator);
    }

}
