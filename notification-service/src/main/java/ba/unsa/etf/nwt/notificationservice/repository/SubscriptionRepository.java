package ba.unsa.etf.nwt.notificationservice.repository;

import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import ba.unsa.etf.nwt.notificationservice.model.SubscriptionConfig;
import com.sun.istack.Nullable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, UUID> {
    boolean existsById(@Nullable UUID id);
    Optional<Subscription> findByTaskIdAndConfig(UUID taskId, SubscriptionConfig config);
    Optional<Subscription> findByIdAndConfig_UserId(UUID id, UUID userId);
}
