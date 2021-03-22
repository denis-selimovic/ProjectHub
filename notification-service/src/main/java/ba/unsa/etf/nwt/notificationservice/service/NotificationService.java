package ba.unsa.etf.nwt.notificationservice.service;

import ba.unsa.etf.nwt.notificationservice.dto.NotificationDTO;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationRepository;
import ba.unsa.etf.nwt.notificationservice.request.CreateNotificationRequest;
import ba.unsa.etf.nwt.notificationservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Notification create(CreateNotificationRequest request, ResourceOwner resourceOwner) {
        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setDescription(request.getDescription());
        notification.setUserId(resourceOwner.getId());
        notification.setRead(false);
        notificationRepository.save(notification);
        return notification;
    }

    public boolean existsById(UUID notificationId) {
        return notificationRepository.existsById(notificationId);
    }

    public void deleteById(UUID notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    public boolean existByUserId(UUID userId) {
        return notificationRepository.existsNotificationByUserId(userId);
    }

    public Page<NotificationDTO> getNotificationsForUser(final UUID userId, final Pageable pageable) {
        return notificationRepository.findAllByUserId(userId, pageable);
    }
}
