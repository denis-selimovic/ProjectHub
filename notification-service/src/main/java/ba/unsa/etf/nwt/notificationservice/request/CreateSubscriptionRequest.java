package ba.unsa.etf.nwt.notificationservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class CreateSubscriptionRequest {
    @NotNull(message = "Task id can't be null")
    @JsonProperty("taskId")
    private UUID taskId;
}
