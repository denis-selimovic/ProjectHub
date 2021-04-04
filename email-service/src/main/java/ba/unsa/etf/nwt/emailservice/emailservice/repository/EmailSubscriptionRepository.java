package ba.unsa.etf.nwt.emailservice.emailservice.repository;

import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailSubscription;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailSubscriptionRepository extends PagingAndSortingRepository<EmailSubscription, UUID> {
    Optional<EmailSubscription> findByTask(UUID task);
}
