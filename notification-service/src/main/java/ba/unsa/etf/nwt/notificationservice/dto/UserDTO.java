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
public class UserDTO implements Resource {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private Instant createdAt;
    private Instant updatedAt;

    public SubscriptionConfig createSubscriptionConfig() {
        SubscriptionConfig config = new SubscriptionConfig();
        config.setUserId(id);
        config.setEmail(email);
        return config;
    }
}
