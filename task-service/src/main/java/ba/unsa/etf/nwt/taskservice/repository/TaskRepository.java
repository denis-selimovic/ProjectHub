package ba.unsa.etf.nwt.taskservice.repository;

import ba.unsa.etf.nwt.taskservice.dto.TaskDTO;
import ba.unsa.etf.nwt.taskservice.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, UUID> {
    Optional<Task> findByNameAndProjectId(String name, UUID projectId);
    Page<TaskDTO> findAll(Specification<Task> specification, Pageable pageable);

    @Query(value = "DELETE FROM issues i WHERE i.project_id = :projectId", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteAllByProjectId(UUID projectId);
}
