package ba.unsa.etf.nwt.notificationservice.repository;

import ba.unsa.etf.nwt.notificationservice.dto.NotificationProjection;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, UUID> {

    boolean existsById(@Nullable UUID notificationId);
    Optional<Notification> findById(@Nullable UUID notificationId);

//    @Query("select n as notification, nu.read as read " +
//            "from Notification n, NotificationUser nu " +
//            "where nu.notification = n and nu.userId = ?1")
    @Query("select n.id, n.title as , n.description, n.createdAt, n.updatedAt, nu.read as read " +
            "from Notification n " +
            "left join NotificationUser nu on nu.notification = n and nu.userId = ?1")
    Page<NotificationProjection> findAllByUserId(UUID userId, Pageable pageable);
}
