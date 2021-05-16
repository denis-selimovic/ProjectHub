package ba.unsa.etf.nwt.projectservice.projectservice.repository;

import ba.unsa.etf.nwt.projectservice.projectservice.filter.FilterProjectRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, UUID>, FilterProjectRepository {
    boolean existsById(@Nullable UUID id);

    @Query(value = "SELECT * FROM projects p where p.owner_id = :ownerId AND p.name = :name AND p.deleted = FALSE",
            nativeQuery = true)
    Optional<Project> findByOwnerIdAndName(UUID ownerId, String name);

    @Override
    @Query(value = "SELECT * FROM projects p where p.id = :id AND p.deleted = FALSE", nativeQuery = true)
    Optional<Project> findById(@NonNull UUID id);

    @Query(value = "SELECT * from projects p where p.id = :id", nativeQuery = true)
    Optional<Project> find(@NonNull UUID id);

    @Override
    @Query(value = "SELECT COUNT(*) FROM projects p WHERE p.deleted = FALSE", nativeQuery = true)
    long count();
}
