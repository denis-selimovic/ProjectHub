package ba.unsa.etf.nwt.userservice.request.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class ConfirmEmailRequest {

    @NotBlank(message = "Token can't be blank")
    @NotNull(message = "Token can't be null")
    private String token;
}
