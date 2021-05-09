package ba.unsa.etf.nwt.emailservice.emailservice.dto;

import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.response.interfaces.Resource;
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

    public EmailConfig createEmailConfig() {
        EmailConfig config = new EmailConfig();
        config.setUserId(id);
        config.setEmail(email);
        return config;
    }
}
