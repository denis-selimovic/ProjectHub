package ba.unsa.etf.nwt.notificationservice.dto;

import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO implements Resource {

    private UUID id;
    private String title;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID userId;

    public NotificationDTO(Notification notification) {
        id = notification.getId();
        title = notification.getTitle();
        description = notification.getDescription();
        createdAt = notification.getCreatedAt();
        updatedAt = notification.getUpdatedAt();
        userId = notification.getUserId();
    }

}
