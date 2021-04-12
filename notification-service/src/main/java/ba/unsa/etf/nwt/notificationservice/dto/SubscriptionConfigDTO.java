package ba.unsa.etf.nwt.notificationservice.dto;

import ba.unsa.etf.nwt.notificationservice.model.SubscriptionConfig;
import ba.unsa.etf.nwt.notificationservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionConfigDTO implements Resource {
    private UUID id;
    private UUID userId;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;

    public SubscriptionConfigDTO(SubscriptionConfig config) {
        id = config.getId();
        userId = config.getUserId();
        email = config.getEmail();
        createdAt = config.getCreatedAt();
        updatedAt = config.getUpdatedAt();
    }
}
