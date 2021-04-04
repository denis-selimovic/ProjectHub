package ba.unsa.etf.nwt.emailservice.emailservice.dto;

import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailSubscription;
import ba.unsa.etf.nwt.emailservice.emailservice.response.interfaces.Resource;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSubscriptionDto implements Resource {

    @NotNull
    @JsonProperty("id")
    private UUID id;

    @NotNull
    @JsonProperty("task_id")
    private UUID taskId;

    @NotNull
    @JsonProperty("user_id")
    private UUID userId;

    @Email
    @JsonProperty("email")
    private String email;

    public EmailSubscriptionDto(EmailSubscription subscription) {
        this.id = subscription.getId();
        this.taskId = subscription.getTask();
        this.userId = subscription.getConfig().getUserId();
        this.email = subscription.getConfig().getEmail();
    }
}
