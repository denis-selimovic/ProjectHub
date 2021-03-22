package ba.unsa.etf.nwt.notificationservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class CreateNotificationRequest {
    @NotBlank(message = "Notification title can't be blank")
    @Size(max = 50, message = "Notification title can have at most 50 characters")
    @JsonProperty("title")
    private String title;

    @NotBlank(message = "Notification description can't be blank")
    @Size(max=200, message = "Notification description can have at most 200 characters")
    @JsonProperty("description")
    private String description;
}
