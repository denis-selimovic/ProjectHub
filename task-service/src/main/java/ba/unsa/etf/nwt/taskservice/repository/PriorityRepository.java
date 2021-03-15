package ba.unsa.etf.nwt.taskservice.repository;

import ba.unsa.etf.nwt.taskservice.model.Priority;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PriorityRepository extends PagingAndSortingRepository<Priority, UUID> {
    List<Priority> findByPriorityType(Priority.PriorityType priorityType);

}
