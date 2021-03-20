package ba.unsa.etf.nwt.projectservice.projectservice.service;

import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.request.CreateProjectRequest;
import ba.unsa.etf.nwt.projectservice.projectservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project create(CreateProjectRequest request, ResourceOwner resourceOwner) {
        Project project = new Project();
        project.setName(request.getName());
        project.setOwnerId(resourceOwner.getId());
        projectRepository.findByOwnerIdAndName(project.getOwnerId(), project.getName()).ifPresent(p -> {
            throw new UnprocessableEntityException("Request body can not be processed. This project already exists");
        });
        return projectRepository.save(project);
    }

    public void delete(UUID id) {
        projectRepository.deleteById(id);
    }

    public Project findById(UUID projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty())
            throw new UnprocessableEntityException("Request body can not be processed. Project with this id doesn't exist");
        return project.get();
    }

    public boolean existsById(UUID id) {
        return projectRepository.existsById(id);
    }

}
