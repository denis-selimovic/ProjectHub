package ba.unsa.etf.nwt.userservice.request.user;

import ba.unsa.etf.nwt.userservice.validation.annotation.FieldMatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@FieldMatch(first = "password", second = "confirmPassword", message = "Password doesn't match confirmation")
public class CreateUserRequest {

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email format")
    @JsonProperty("email")
    private final String email;

    @Size(min = 8, message = "Password must contain at least eight characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=()!?.\"]).{4,}",
            message = "Password must contain at least one lowercase, one uppercase, one digit and one special character")
    @JsonProperty("password")
    private final String password;

    @JsonProperty("confirm_password")
    private final String confirmPassword;

    @Size(max = 32, message = "First name can't be longer than thirty two characters")
    @NotBlank(message = "First name can't be blank")
    @JsonProperty("first_name")
    private final String firstName;

    @Size(max = 32, message = "Last name can't be longer than thirty two characters")
    @NotBlank(message = "Last name can't be blank")
    @JsonProperty("last_name")
    private final String lastName;
}
