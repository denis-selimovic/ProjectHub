package ba.unsa.etf.nwt.notificationservice.dto;

import java.time.Instant;
import java.util.UUID;

public interface NotificationProjection {
    UUID getId();
    String getTitle();
    String getDescription();
    Instant getCreatedAt();
    Instant getUpdatedAt();
    Boolean getRead();
}
