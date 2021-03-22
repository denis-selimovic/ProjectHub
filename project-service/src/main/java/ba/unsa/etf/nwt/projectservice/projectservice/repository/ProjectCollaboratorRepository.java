package ba.unsa.etf.nwt.projectservice.projectservice.repository;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectCollaboratorDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectCollaboratorRepository extends PagingAndSortingRepository<ProjectCollaborator, UUID> {
    Optional<ProjectCollaborator> findByCollaboratorIdAndProjectId(UUID userId, UUID projectId);
    Page<ProjectCollaboratorDTO> findAllByProject(Project project, Pageable pageable);
}
