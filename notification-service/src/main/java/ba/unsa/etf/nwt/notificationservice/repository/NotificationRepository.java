package ba.unsa.etf.nwt.notificationservice.repository;

import ba.unsa.etf.nwt.notificationservice.model.Notification;
import com.sun.istack.Nullable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, UUID> {
    boolean existsById(@Nullable UUID notificationId);


}
