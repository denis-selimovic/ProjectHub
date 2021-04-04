package ba.unsa.etf.nwt.emailservice.emailservice.request;

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
public class CreateConfigRequest {

    @Email
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("user_id")
    private UUID userId;
}
