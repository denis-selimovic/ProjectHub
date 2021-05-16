package ba.unsa.etf.nwt.projectservice.projectservice.repository;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectCollaboratorDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectCollaboratorRepository extends PagingAndSortingRepository<ProjectCollaborator, UUID> {
    @Query(
            value = "SELECT * FROM project_collaborators pc " +
                    "INNER JOIN projects p on p.id = pc.project_id WHERE pc.collaborator_id = :userId " +
                    "AND pc.project_id = :projectId AND p.deleted = FALSE",
            nativeQuery = true
    )
    Optional<ProjectCollaborator> findByCollaboratorIdAndProjectId(UUID userId, UUID projectId);

    Page<ProjectCollaboratorDTO> findAllByProject(Project project, Pageable pageable);

    @Override
    @Query(
            value = "SELECT COUNT(*) from project_collaborators pc INNER JOIN projects p on p.id = pc.project_id " +
                    "WHERE p.deleted = FALSE",
            nativeQuery = true
    )
    long count();
}
