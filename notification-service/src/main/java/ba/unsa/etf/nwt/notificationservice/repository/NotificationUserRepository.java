package ba.unsa.etf.nwt.notificationservice.repository;

import ba.unsa.etf.nwt.notificationservice.dto.NotificationProjection;
import ba.unsa.etf.nwt.notificationservice.model.NotificationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationUserRepository extends PagingAndSortingRepository<NotificationUser, UUID> {
    Optional<NotificationUser> findByNotification_IdAndUserId(UUID notificationId, UUID userId);

    @Query("select n as notification, nu.read as read " +
            "from NotificationUser nu, Notification n  " +
            "where n = nu.notification and nu.userId = ?1")
    Page<NotificationProjection> findNotificationByUser(UUID userId, Pageable pageable);
}
