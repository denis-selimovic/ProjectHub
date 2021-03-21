package ba.unsa.etf.nwt.projectservice.projectservice.service;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectCollaboratorDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectCollaboratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectCollaboratorService {
    private final ProjectCollaboratorRepository projectCollaboratorRepository;


    public ProjectCollaborator createCollaborator(UUID collaboratorId, Project project) {
        projectCollaboratorRepository.findByProject(project).ifPresent(pc -> {
            throw new UnprocessableEntityException("Collaborator already added to this project");
        });

        ProjectCollaborator projectCollaborator = new ProjectCollaborator();
        projectCollaborator.setProject(project);
        projectCollaborator.setCollaboratorId(collaboratorId);
        return projectCollaboratorRepository.save(projectCollaborator);
    }

    public ProjectCollaborator findById(UUID collaboratorId) {
        Optional<ProjectCollaborator> projectCollaborator = projectCollaboratorRepository.findById(collaboratorId);
        if (projectCollaborator.isEmpty())
            throw new UnprocessableEntityException("Request body can not be processed");
        return projectCollaborator.get();
    }

    public ProjectCollaborator findByIdAndAndProjectId(UUID id, UUID projectId) {
        return projectCollaboratorRepository.findByIdAndProjectId(id, projectId).orElseThrow(() -> {
            throw new NotFoundException("Collaborator not found");
        });
    }

    public boolean existsByCollaboratorIdAndProjectId(UUID userId, UUID projectId) {
        return projectCollaboratorRepository.findByCollaboratorIdAndProjectId(userId, projectId).isEmpty();
    }

    public void delete(ProjectCollaborator projectCollaborator) {
        projectCollaboratorRepository.delete(projectCollaborator);
    }

    public Page<ProjectCollaboratorDTO> getCollaboratorsForProject(Project project, Pageable pageable) {
        return projectCollaboratorRepository.findAllByProject(project, pageable);
    }
}
