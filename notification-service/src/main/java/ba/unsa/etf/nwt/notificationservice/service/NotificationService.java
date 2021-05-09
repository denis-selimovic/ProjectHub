package ba.unsa.etf.nwt.notificationservice.service;

import ba.unsa.etf.nwt.notificationservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.model.NotificationUser;
import ba.unsa.etf.nwt.notificationservice.dto.NotificationDTO;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationRepository;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationUserRepository;
import ba.unsa.etf.nwt.notificationservice.request.CreateNotificationRequest;
import ba.unsa.etf.nwt.notificationservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationUserRepository notificationUserRepository;

    public NotificationDTO create(CreateNotificationRequest request, ResourceOwner resourceOwner) {
        Notification notification = create(request.getTitle(), request.getDescription());
        NotificationUser notificationUser = createNotificationUser(resourceOwner.getId(), notification);
        return new NotificationDTO(notification, notificationUser.getRead());
    }

    public Notification create(String title, String description) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setDescription(description);
        return notificationRepository.save(notification);
    }

    public NotificationUser createNotificationUser(UUID userId, Notification notification) {
        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setUserId(userId);
        notificationUser.setNotification(notification);
        notificationUser.setRead(false);
        return notificationUserRepository.save(notificationUser);
    }

    public Page<NotificationDTO> getNotificationsForUser(final UUID userId, final Pageable pageable) {
        return notificationUserRepository.findNotificationByUser(userId, pageable);
    }

    public Notification findById(UUID notificationId) {
        if (notificationRepository.findById(notificationId).isPresent())
            return notificationRepository.findById(notificationId).get();

        throw new NotFoundException("Notification not found");
    }

    public void delete(final UUID notificationId, final UUID userId) {
        Optional<NotificationUser> notificationUserOptional = notificationUserRepository
                .findByNotification_IdAndUserId(notificationId, userId);

        if (notificationUserOptional.isEmpty()) {
            throw new UnprocessableEntityException("Notification doesn't exist");
        }

        notificationUserRepository.deleteById(notificationUserOptional.get().getId());
    }
}
