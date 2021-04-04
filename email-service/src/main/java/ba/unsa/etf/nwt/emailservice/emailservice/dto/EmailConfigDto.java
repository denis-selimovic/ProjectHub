package ba.unsa.etf.nwt.emailservice.emailservice.dto;

import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.response.interfaces.Resource;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfigDto implements Resource {

    @NotNull
    @JsonProperty("id")
    private UUID id;

    @NotNull
    @JsonProperty("user_id")
    private UUID userId;

    @Email
    @JsonProperty("email")
    private String email;

    public EmailConfigDto(EmailConfig config) {
        this.id = config.getId();
        this.userId = config.getUserId();
        this.email = config.getEmail();
    }
}
