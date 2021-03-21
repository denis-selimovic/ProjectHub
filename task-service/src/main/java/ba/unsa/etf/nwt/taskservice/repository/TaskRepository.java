package ba.unsa.etf.nwt.taskservice.repository;

import ba.unsa.etf.nwt.taskservice.dto.TaskDTO;
import ba.unsa.etf.nwt.taskservice.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, UUID>, JpaSpecificationExecutor<Task> {
    Optional<Task> findByNameAndProjectId(String name, UUID projectId);
    Page<TaskDTO> findAll(Pageable pageable, Specification<Task> specification);
}
