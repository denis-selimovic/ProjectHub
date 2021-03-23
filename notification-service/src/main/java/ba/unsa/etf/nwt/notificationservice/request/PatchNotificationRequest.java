package ba.unsa.etf.nwt.notificationservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchNotificationRequest {
    @NotBlank
    private JsonNullable<Boolean> read = JsonNullable.undefined();
}
