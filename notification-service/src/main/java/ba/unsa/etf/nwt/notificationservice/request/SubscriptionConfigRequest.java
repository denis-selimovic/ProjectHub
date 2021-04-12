package ba.unsa.etf.nwt.notificationservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionConfigRequest {
    @NotNull(message = "User id can't be null")
    @JsonProperty("user_id")
    private UUID userId;

    @NotBlank(message = "Email can't be blank")
    @Size(max = 50, message = "Email can contain at most 50 characters")
    @JsonProperty("email")
    private String email;
}
