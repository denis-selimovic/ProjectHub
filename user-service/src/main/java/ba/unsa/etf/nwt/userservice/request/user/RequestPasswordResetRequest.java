package ba.unsa.etf.nwt.userservice.request.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class RequestPasswordResetRequest {

    @Email(message = "Invalid email format")
    @NotNull(message = "Email can't be null")
    private String email;
}
