package ba.unsa.etf.nwt.projectservice.projectservice.service;

import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.request.CreateProjectRequest;
import ba.unsa.etf.nwt.projectservice.projectservice.filter.ProjectFilter;
import ba.unsa.etf.nwt.projectservice.projectservice.request.PatchProjectRequest;
import ba.unsa.etf.nwt.projectservice.projectservice.security.ResourceOwner;
import ba.unsa.etf.nwt.projectservice.projectservice.utility.JsonNullableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Project> findByFilter(final ProjectFilter filter, Pageable pageable) {
        if(filter.getOwnerId() != null) {
            return projectRepository.findAllByOwnerIdAndDeletedIsFalse(filter.getOwnerId(), pageable);
        }

        if (filter.getCollaboratorId() != null) {
            return projectRepository.findAllByCollaboratorIdAndDeletedIsFalse(filter.getCollaboratorId(), pageable);
        }

        return projectRepository.findAll(pageable);
    }

    public void patch(final Project project, final PatchProjectRequest patchProjectRequest) {
        JsonNullableUtils.changeIfPresent(patchProjectRequest.getName(), project::setName);
        JsonNullableUtils.changeIfPresent(patchProjectRequest.getOwnerId(), project::setOwnerId);
        projectRepository.save(project);
    }

    public void checkIfOwner(UUID ownerId, UUID resourceOwnerId) {
        if (!ownerId.equals(resourceOwnerId))
            throw new ForbiddenException("You don't have permission for this activity");
    }

    public void softDelete(Project project) {
        project.setDeleted(true);
        projectRepository.save(project);
    }
}
