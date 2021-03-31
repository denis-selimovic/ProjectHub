package ba.unsa.etf.nwt.notificationservice.dto;

import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.model.NotificationUser;
import ba.unsa.etf.nwt.notificationservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationUserDTO implements Resource {

    private UUID id;
    private Notification notification;
    private UUID userId;
    private boolean read;

    public NotificationUserDTO(NotificationUser notificationUser) {
        id = notificationUser.getId();
        notification = notificationUser.getNotification();
        userId = notificationUser.getUserId();
        read = notificationUser.getRead();

    }

}
