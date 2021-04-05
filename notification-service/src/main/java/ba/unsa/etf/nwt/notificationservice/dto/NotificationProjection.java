package ba.unsa.etf.nwt.notificationservice.dto;

import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.response.interfaces.Resource;

public interface NotificationProjection extends Resource {
    Notification getNotification();
    Boolean getRead();
}
