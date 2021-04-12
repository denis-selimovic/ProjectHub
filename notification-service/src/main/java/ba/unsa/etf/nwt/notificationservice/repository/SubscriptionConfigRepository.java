package ba.unsa.etf.nwt.notificationservice.repository;

import ba.unsa.etf.nwt.notificationservice.model.SubscriptionConfig;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionConfigRepository extends PagingAndSortingRepository<SubscriptionConfig, UUID> {
    Optional<SubscriptionConfig> findByEmailOrUserId(String email, UUID userId);
    Optional<SubscriptionConfig> findByUserId(UUID userId);
}
