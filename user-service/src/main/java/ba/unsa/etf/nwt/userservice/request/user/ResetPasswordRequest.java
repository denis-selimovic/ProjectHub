package ba.unsa.etf.nwt.userservice.request.user;

import ba.unsa.etf.nwt.userservice.validation.annotation.FieldMatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@FieldMatch(first = "password", second = "confirmPassword", message = "Password doesn't much confirmation")
public class ResetPasswordRequest {

    @Size(min = 8, message = "Password must contain at least eight characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=()!?.\"]).{8,}",
            message = "Password must contain at least one lowercase, one uppercase, one digit and one special character")
    @JsonProperty("password")
    private final String password;

    @JsonProperty("confirm_password")
    private final String confirmPassword;

    @NotBlank(message = "Token can't be blank")
    @NotNull(message = "Token can't be null")
    private String token;
}
