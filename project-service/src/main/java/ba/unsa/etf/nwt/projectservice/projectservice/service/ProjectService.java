package ba.unsa.etf.nwt.projectservice.projectservice.service;

import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.request.CreateProjectRequest;
import ba.unsa.etf.nwt.projectservice.projectservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            throw new UnprocessableEntityException("Project with this name already exists");
        });
        return projectRepository.save(project);
    }

    public void delete(UUID id) {
        projectRepository.deleteById(id);
    }

    public Project findById(UUID projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> {
            throw new NotFoundException("Project not found");
        });
    }

}
