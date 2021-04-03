package ba.unsa.etf.nwt.userservice.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailRequest {
    @JsonProperty(value = "type")
    @Pattern(regexp = "activation|reset")
    private String type;

    @JsonProperty(value = "email")
    @Email
    private String email;

    @JsonProperty(value = "first_name")
    @NotBlank
    private String firstName;

    @JsonProperty(value = "token")
    @NotBlank
    private String token;
}
