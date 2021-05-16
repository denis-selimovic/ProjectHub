package ba.unsa.etf.nwt.userservice.controller;

import ba.unsa.etf.nwt.userservice.client.service.EmailService;
import ba.unsa.etf.nwt.userservice.dto.UserDTO;
import ba.unsa.etf.nwt.userservice.messaging.publishers.UserPublisher;
import ba.unsa.etf.nwt.userservice.model.Token;
import ba.unsa.etf.nwt.userservice.model.User;
import ba.unsa.etf.nwt.userservice.request.user.ConfirmEmailRequest;
import ba.unsa.etf.nwt.userservice.request.user.CreateUserRequest;
import ba.unsa.etf.nwt.userservice.request.user.RequestPasswordResetRequest;
import ba.unsa.etf.nwt.userservice.request.user.ResetPasswordRequest;
import ba.unsa.etf.nwt.userservice.response.base.ErrorResponse;
import ba.unsa.etf.nwt.userservice.response.base.Response;
import ba.unsa.etf.nwt.userservice.response.base.SimpleResponse;
import ba.unsa.etf.nwt.userservice.service.TokenService;
import ba.unsa.etf.nwt.userservice.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final UserPublisher userPublisher;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User account created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "422",
                    description = "Validation fail or email in use",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Response<UserDTO>> create(@RequestBody @Valid CreateUserRequest request) {
        User user = userService.create(request);
        Token token = tokenService.generateToken(user, Token.TokenType.ACTIVATE_ACCOUNT);
        emailService.sendEmail(user, token, "activation");
        userPublisher.send(new UserDTO(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(new UserDTO(user)));
    }

    @PostMapping("/request-password-reset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reset password email sent",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimpleResponse.class))}),
            @ApiResponse(responseCode = "422",
                    description = "Validation fail",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> requestPasswordReset(@RequestBody @Valid RequestPasswordResetRequest request) {
        userService.findByEmail(request.getEmail()).ifPresent(user -> {
            Token token = tokenService.generateToken(user, Token.TokenType.RESET_PASSWORD);
            emailService.sendEmail(user, token, "reset");
        });
        return ResponseEntity.ok(new Response<>(new SimpleResponse("Email successfully sent")));
    }

    @PostMapping("/confirm-email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Account activated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimpleResponse.class))}),
            @ApiResponse(responseCode = "422",
                    description = "Validation fail or user already confirmed email",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> confirmEmail(@RequestBody @Valid ConfirmEmailRequest request) {
        tokenService.confirmActivation(request);
        return ResponseEntity.ok(new Response<>(new SimpleResponse("Account successfully activated")));
    }

    @PostMapping("/reset-password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Password changed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimpleResponse.class))}),
            @ApiResponse(responseCode = "422",
                    description = "Validation fail or email not confirmed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        User user = tokenService.confirmResetPassword(request);
        userService.resetPassword(user, request);
        return ResponseEntity.ok(new Response<>(new SimpleResponse("Password successfully changed")));
    }

    @GetMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimpleResponse.class))}),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<UserDTO>> getUserById(@PathVariable UUID userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok().body(new Response<>(new UserDTO(user)));
    }

    @GetMapping("user-details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimpleResponse.class))}),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<UserDTO>> getUserDetails(Principal principal) {
        User user = userService.getUserDetails(principal.getName());
        return ResponseEntity.ok().body(new Response<>(new UserDTO(user)));
    }
}
