package ba.unsa.etf.nwt.notificationservice.service;

import ba.unsa.etf.nwt.notificationservice.dto.MetadataDTO;
import ba.unsa.etf.nwt.notificationservice.dto.NotificationDTO;
import ba.unsa.etf.nwt.notificationservice.projections.NotificationProjection;
import ba.unsa.etf.nwt.notificationservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.model.NotificationUser;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationRepository;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationUserRepository;
import ba.unsa.etf.nwt.notificationservice.request.CreateNotificationRequest;
import ba.unsa.etf.nwt.notificationservice.response.base.PaginatedResponse;
import ba.unsa.etf.nwt.notificationservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationUserRepository notificationUserRepository;

    public NotificationDTO create(CreateNotificationRequest request, ResourceOwner resourceOwner) {
        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setDescription(request.getDescription());
        notificationRepository.save(notification);
        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setUserId(resourceOwner.getId());
        notificationUser.setNotification(notification);
        notificationUser.setRead(false);
        notificationUserRepository.save(notificationUser);
        return new NotificationDTO(notification);
    }

    public PaginatedResponse<NotificationDTO, MetadataDTO> getNotificationsForUser(final UUID userId, final Pageable pageable) {
        Page<NotificationProjection> notification = notificationUserRepository.findNotificationByUser(userId, pageable);
        List<NotificationDTO> notifications = notification
                .getContent()
                .stream()
                .map(np -> {
                    NotificationDTO dto = new NotificationDTO(np.getNotification());
                    dto.setRead(np.getRead());
                    return dto;
                }).collect(Collectors.toList());
        return new PaginatedResponse<>(new MetadataDTO(notification), notifications);
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
