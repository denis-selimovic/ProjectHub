package ba.unsa.etf.nwt.notificationservice.dto;

import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import ba.unsa.etf.nwt.notificationservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO implements Resource {

    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID userId;
    private UUID taskId;

    public SubscriptionDTO(Subscription subscription) {
        id = subscription.getId();
        createdAt = subscription.getCreatedAt();
        updatedAt = subscription.getUpdatedAt();
        userId = subscription.getUserId();
        taskId = subscription.getTaskId();
    }
}
