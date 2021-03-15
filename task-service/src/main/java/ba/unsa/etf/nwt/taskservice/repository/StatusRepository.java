package ba.unsa.etf.nwt.taskservice.repository;

import ba.unsa.etf.nwt.taskservice.model.Status;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatusRepository extends PagingAndSortingRepository<Status, UUID> {
    Optional<Status> findByStatus(Status.StatusType status);
}
