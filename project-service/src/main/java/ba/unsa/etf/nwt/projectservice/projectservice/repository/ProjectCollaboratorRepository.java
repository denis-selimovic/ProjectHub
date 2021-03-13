package ba.unsa.etf.nwt.projectservice.projectservice.repository;

import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectCollaboratorRepository extends PagingAndSortingRepository<ProjectCollaborator, UUID> {
}
