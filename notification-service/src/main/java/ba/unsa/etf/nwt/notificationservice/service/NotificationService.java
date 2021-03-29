package ba.unsa.etf.nwt.notificationservice.service;

import ba.unsa.etf.nwt.notificationservice.dto.NotificationDTO;
import ba.unsa.etf.nwt.notificationservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.notificationservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationRepository;
import ba.unsa.etf.nwt.notificationservice.request.CreateNotificationRequest;
import ba.unsa.etf.nwt.notificationservice.request.PatchNotificationRequest;
import ba.unsa.etf.nwt.notificationservice.security.ResourceOwner;
import ba.unsa.etf.nwt.notificationservice.utility.JsonNullableUtils;
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

    public void deleteById(UUID notificationId) {
        if(!notificationRepository.existsById(notificationId))
            throw new UnprocessableEntityException("Request body can not be processed. This notification doesn't exist");

        notificationRepository.deleteById(notificationId);
    }

    public Page<NotificationDTO> getNotificationsForUser(final UUID userId, final Pageable pageable) {
        return notificationRepository.findAllByUserId(userId, pageable);
    }

    public Notification patch(final Notification notification, final PatchNotificationRequest patchNotificationRequest) {
        JsonNullableUtils.changeIfPresent(patchNotificationRequest.getRead(), notification::setRead);
        return notificationRepository.save(notification);
    }

    public void checkUserId(UUID resourceOwnerId, UUID userId) {
        if(!resourceOwnerId.equals(userId))
            throw new ForbiddenException("Forbidden");
    }

    public Notification findById(UUID notificationId) {
        if (notificationRepository.findById(notificationId).isPresent())
            return notificationRepository.findById(notificationId).get();

        throw new NotFoundException("Notification not found");
    }
}
