package ba.unsa.etf.nwt.notificationservice.service;

import ba.unsa.etf.nwt.notificationservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.model.NotificationUser;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationUserRepository;
import ba.unsa.etf.nwt.notificationservice.request.PatchNotificationRequest;
import ba.unsa.etf.nwt.notificationservice.utility.JsonNullableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationUserService {
    private final NotificationUserRepository notificationUserRepository;

    public NotificationUser findByNotificationAndUserId(final Notification notification, final UUID userId) {
        return notificationUserRepository
                .findByNotification_IdAndUserId(notification.getId(), userId)
                .orElseThrow(() -> new NotFoundException("Not found"));
    }

    public NotificationUser patch(final NotificationUser notificationUser,
                      final PatchNotificationRequest patchNotificationRequest) {
        JsonNullableUtils.changeIfPresent(patchNotificationRequest.getRead(), notificationUser::setRead);
        return notificationUserRepository.save(notificationUser);
    }
}
