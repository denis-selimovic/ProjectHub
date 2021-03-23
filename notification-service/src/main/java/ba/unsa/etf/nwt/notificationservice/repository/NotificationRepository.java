package ba.unsa.etf.nwt.notificationservice.repository;

import ba.unsa.etf.nwt.notificationservice.dto.NotificationDTO;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, UUID> {

    boolean existsById(@Nullable UUID notificationId);
    Optional<Notification> findById(@Nullable UUID notificationId);

    Page<NotificationDTO> findAllByUserId(@NonNull UUID userId, @NonNull Pageable pageable);
}
