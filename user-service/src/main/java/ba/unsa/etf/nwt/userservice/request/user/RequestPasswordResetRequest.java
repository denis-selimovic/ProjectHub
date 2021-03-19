package ba.unsa.etf.nwt.userservice.request.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class RequestPasswordResetRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email can't be blank")
    private String email;
}
