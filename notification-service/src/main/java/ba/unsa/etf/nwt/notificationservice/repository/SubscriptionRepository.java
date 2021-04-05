package ba.unsa.etf.nwt.notificationservice.repository;

import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import com.sun.istack.Nullable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, UUID> {
    Optional<Subscription> findByTaskIdAndUserId(UUID taskId, UUID userID);
    boolean existsById(@Nullable UUID id);
    Optional<Subscription> findByIdAndUserId(UUID id, UUID userId);
}
