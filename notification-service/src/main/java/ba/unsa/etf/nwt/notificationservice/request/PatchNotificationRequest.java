package ba.unsa.etf.nwt.notificationservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchNotificationRequest {
    @NotNull(message = "Notification Read attribute can't be null")
    @JsonProperty("read")
    private JsonNullable<Boolean> read = JsonNullable.undefined();
}
