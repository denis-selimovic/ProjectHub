package ba.unsa.etf.nwt.projectservice.projectservice.repository;

import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, UUID> {
}
