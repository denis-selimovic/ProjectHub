package ba.unsa.etf.nwt.userservice.controller;

import ba.unsa.etf.nwt.userservice.dto.UserDTO;
import ba.unsa.etf.nwt.userservice.model.Token;
import ba.unsa.etf.nwt.userservice.model.User;
import ba.unsa.etf.nwt.userservice.request.user.ConfirmEmailRequest;
import ba.unsa.etf.nwt.userservice.request.user.CreateUserRequest;
import ba.unsa.etf.nwt.userservice.request.user.RequestPasswordResetRequest;
import ba.unsa.etf.nwt.userservice.request.user.ResetPasswordRequest;
import ba.unsa.etf.nwt.userservice.response.base.ErrorResponse;
import ba.unsa.etf.nwt.userservice.response.base.Response;
import ba.unsa.etf.nwt.userservice.response.base.SimpleResponse;
import ba.unsa.etf.nwt.userservice.service.EmailService;
import ba.unsa.etf.nwt.userservice.service.TokenService;
import ba.unsa.etf.nwt.userservice.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final TokenService tokenService;
    private final EmailService emailService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created"),
            @ApiResponse(code = 422,
                    message = "Unprocessable entity: Validation fail or email in use",
                    response = ErrorResponse.class
            )
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Response<UserDTO>> create(@RequestBody @Valid CreateUserRequest request) {
        User user = userService.create(request);
        Token token = tokenService.generateToken(user, Token.TokenType.ACTIVATE_ACCOUNT);
        emailService.sendEmail(user, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(new UserDTO(user)));
    }

    @PostMapping("/request-password-reset")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reset password email sent"),
            @ApiResponse(code = 422, message = "Unprocessable entity: Validation fail", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> requestPasswordReset(@RequestBody @Valid RequestPasswordResetRequest request) {
        userService.findByEmail(request.getEmail()).ifPresent(user -> {
            Token token = tokenService.generateToken(user, Token.TokenType.RESET_PASSWORD);
            emailService.sendEmail(user, token);
        });
        return ResponseEntity.ok(new Response<>(new SimpleResponse("Email successfully sent")));
    }

    @PostMapping("/confirm-email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account activated"),
            @ApiResponse(code = 422,
                    message = "Unprocessable entity: Validation fail or email already confirmed",
                    response = ErrorResponse.class
            )
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> confirmEmail(@RequestBody @Valid ConfirmEmailRequest request) {
        tokenService.confirmActivation(request);
        return ResponseEntity.ok(new Response<>(new SimpleResponse("Account successfully activated")));
    }

    @PostMapping("/reset-password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password changed"),
            @ApiResponse(code = 422,
                    message = "Unprocessable entity: Validation fail or mail is not confirmed",
                    response = ErrorResponse.class
            )
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        User user = tokenService.confirmResetPassword(request);
        userService.resetPassword(user, request);
        return ResponseEntity.ok(new Response<>(new SimpleResponse("Password successfully changed")));
    }
}
