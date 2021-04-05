package ba.unsa.etf.nwt.emailservice.emailservice.request;

import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailSubscription;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubscriptionRequest {

    @NotNull
    @JsonProperty("task_id")
    private UUID taskId;

    public EmailSubscription createSubscription() {
        EmailSubscription emailSubscription = new EmailSubscription();
        emailSubscription.setTask(taskId);
        return emailSubscription;
    }
}
