package ba.unsa.etf.nwt.notificationservice.repository;

import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, UUID> {
}
