package ba.unsa.etf.nwt.userservice.client.request;

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
    @Pattern(regexp = "activation|reset")
    private String type;
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String token;
}
