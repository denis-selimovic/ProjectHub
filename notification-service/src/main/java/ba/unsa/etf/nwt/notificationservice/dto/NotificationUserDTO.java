package ba.unsa.etf.nwt.notificationservice.dto;

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
    private UUID notificationId;
    private UUID userId;

    public NotificationUserDTO(NotificationUser notificationUser) {
        id = notificationUser.getId();
        notificationId = notificationUser.getNotificationId();
        userId = notificationUser.getUserId();
    }

}
