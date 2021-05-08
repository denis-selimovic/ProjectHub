package ba.unsa.etf.nwt.userservice.controller;

import ba.unsa.etf.nwt.userservice.client.dto.EmailDTO;
import ba.unsa.etf.nwt.userservice.client.service.EmailService;
import ba.unsa.etf.nwt.userservice.dto.UserDTO;
import ba.unsa.etf.nwt.userservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.userservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.userservice.messaging.publishers.UserPublisher;
import ba.unsa.etf.nwt.userservice.model.Token;
import ba.unsa.etf.nwt.userservice.model.User;
import ba.unsa.etf.nwt.userservice.repository.TokenRepository;
import ba.unsa.etf.nwt.userservice.repository.UserRepository;
import ba.unsa.etf.nwt.userservice.request.user.CreateUserRequest;
import ba.unsa.etf.nwt.userservice.service.TokenService;
import ba.unsa.etf.nwt.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private EmailService emailService;
    @MockBean
    private UserPublisher userPublisher;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testBlankEmail() throws Exception {
        String error = "Email can't be blank";
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "",
                            "password": "Password1#",
                            "confirm_password": "Password1#",
                            "first_name": "Lamija",
                            "last_name": "Vrnjak"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.email").value(hasItem(error)));
    }

    @Test
    public void testNoEmail() throws Exception {
        String error = "Email can't be blank";
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "password": "Password1#",
                            "confirm_password": "Password1#",
                            "first_name": "Lamija",
                            "last_name": "Vrnjak"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.email").value(hasItem(error)));
    }

    @Test
    public void testInvalidEmail() throws Exception {
        String error = "Invalid email format";
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lamija.vrnjak",
                            "password": "Password1#",
                            "confirm_password": "Password1#",
                            "first_name": "Lamija",
                            "last_name": "Vrnjak"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.email").value(hasItem(error)));
    }

    @Test
    public void testPasswordsNotMatching() throws Exception {
        String error = "Password doesn't match confirmation";
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lamija.vrnjak@gmail.com",
                            "password": "Password1#",
                            "confirm_password": "passwordd",
                            "first_name": "Lamija",
                            "last_name": "Vrnjak"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.password").value(hasItem(error)));
    }

    @Test
    public void testPasswordNoUppercase() throws Exception {
        String error = "Password must contain at least one lowercase, one uppercase, one digit and one special character";
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lamija.vrnjak@gmail.com",
                            "password": "password1#",
                            "confirm_password": "password1#",
                            "first_name": "Lamija",
                            "last_name": "Vrnjak"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.password").value(hasItem(error)));
    }

    @Test
    public void testPasswordTooShort() throws Exception {
        String error = "Password must contain at least eight characters";
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lamija.vrnjak@gmail.com",
                            "password": "Pa1#",
                            "confirm_password": "Pa1#",
                            "first_name": "Lamija",
                            "last_name": "Vrnjak"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.password").value(hasItem(error)));
    }

    @Test
    public void testPasswordMultipleViolations() throws Exception {
        String[] errors = {
                "Password doesn't match confirmation",
                "Password must contain at least eight characters",
                "Password must contain at least one lowercase, one uppercase, one digit and one special character"
        };
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lamija.vrnjak@gmail.com",
                            "password": "Pa1",
                            "confirm_password": "Pa1#",
                            "first_name": "Lamija",
                            "last_name": "Vrnjak"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.password").value(containsInAnyOrder(errors)));
    }

    @Test
    public void testBlankNameAndSurname() throws Exception {
        String errorName = "First name can't be blank";
        String errorSurname = "Last name can't be blank";
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lamija.vrnjak@gmail.com",
                            "password": "Password1#",
                            "confirm_password": "Password1#",
                            "first_name": "",
                            "last_name": ""
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.first_name").value(hasItem(errorName)))
                .andExpect(jsonPath("$.errors.last_name").value(hasItem(errorSurname)));
    }

    @Test
    public void testNoNameAndSurname() throws Exception {
        String errorName = "First name can't be blank";
        String errorSurname = "Last name can't be blank";
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lamija.vrnjak@gmail.com",
                            "password": "Password1#",
                            "confirm_password": "Password1#"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.first_name").value(hasItem(errorName)))
                .andExpect(jsonPath("$.errors.last_name").value(hasItem(errorSurname)));
    }

    @Test
    public void testTooLongNameAndSurname() throws Exception {
        String errorName = "First name can't be longer than thirty two characters";
        String errorSurname = "Last name can't be longer than thirty two characters";
        String tooLong = "a".repeat(33);
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "email": "lamija.vrnjak@gmail.com",
                            "password": "Password1#",
                            "confirm_password": "Password1#",
                            "first_name": "%s",
                            "last_name": "%s"
                        }""", tooLong, tooLong)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.first_name").value(hasItem(errorName)))
                .andExpect(jsonPath("$.errors.last_name").value(hasItem(errorSurname)));
    }

    @Test
    public void testSuccessCreate() throws Exception {
        Mockito.doNothing().when(userPublisher).send(Mockito.any(UserDTO.class));
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lamija.vrnjak@gmail.com",
                            "password": "Password1#",
                            "confirm_password": "Password1#",
                            "first_name": "Lamija",
                            "last_name": "Vrnjak"
                        }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").hasJsonPath())
                .andExpect(jsonPath("$.data.email").hasJsonPath())
                .andExpect(jsonPath("$.data.first_name").hasJsonPath())
                .andExpect(jsonPath("$.data.last_name").hasJsonPath())
                .andExpect(jsonPath("$.data.created_at").hasJsonPath())
                .andExpect(jsonPath("$.data.updated_at").hasJsonPath());
    }

    @Test
    public void testEmailInUse() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "amila.zigo@gmail.com",
                            "password": "Password1#",
                            "confirm_password": "Password1#",
                            "first_name": "Amila",
                            "last_name": "Zigo"
                        }"""
                )
        );

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "amila.zigo@gmail.com",
                            "password": "Password1#",
                            "confirm_password": "Password1#",
                            "first_name": "Druga Amila",
                            "last_name": "Zigo"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("Request body can not be processed")));
    }

    @Test
    public void testEmailServiceUnprocessableEntityError() throws Exception {
        Mockito.when(emailService.sendEmail(Mockito.any(), Mockito.any(), eq("activation")))
                .thenThrow(new UnprocessableEntityException("Unprocessable entity"));
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "amila.zigo@gmail.com",
                            "password": "Password1#",
                            "confirm_password": "Password1#",
                            "first_name": "Amila",
                            "last_name": "Zigo"
                        }"""
                )).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testEmailServiceNotFoundError() throws Exception {
        Mockito.when(emailService.sendEmail(Mockito.any(), Mockito.any(), eq("activation")))
                .thenThrow(new NotFoundException("Not found"));
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "amila.zigo@gmail.com",
                            "password": "Password1#",
                            "confirm_password": "Password1#",
                            "first_name": "Amila",
                            "last_name": "Zigo"
                        }"""
                )).andExpect(status().isNotFound());
    }

    @Test
    public void testRequestResendBlankEmail() throws Exception {
        mockMvc.perform(post("/api/v1/users/request-password-reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": ""
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.email", hasSize(1)))
                .andExpect(jsonPath("$.errors.email", hasItem("Email can't be blank")));
    }

    @Test
    public void testRequestResendNoEmail() throws Exception {
        mockMvc.perform(post("/api/v1/users/request-password-reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.email", hasSize(1)))
                .andExpect(jsonPath("$.errors.email", hasItem("Email can't be blank")));
    }

    @Test
    public void testRequestResendSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/users/request-password-reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lamija.vrnjak@gmail.com"
                        }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", is("Email successfully sent")));
    }

    @Test
    public void testEmailServiceErrorOnPasswordReset() throws Exception {
        createUserInDb("lamija.vrnjak@gmail.com", "password", "Test", "Test", true);
        Mockito.when(emailService.sendEmail(Mockito.any(), Mockito.any(), eq("reset")))
                .thenThrow(new NotFoundException("Not found"));
        mockMvc.perform(post("/api/v1/users/request-password-reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lamija.vrnjak@gmail.com"
                        }""")
                ).andExpect(status().isNotFound());
    }

    @Test
    public void testEmailServiceUnprocessableEntityErrorOnPasswordReset() throws Exception {
        createUserInDb("lamija.vrnjak@gmail.com", "password", "Test", "Test", true);
        Mockito.when(emailService.sendEmail(Mockito.any(), Mockito.any(), eq("reset")))
                .thenThrow(new UnprocessableEntityException("Unprocessable entity"));
        mockMvc.perform(post("/api/v1/users/request-password-reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "lamija.vrnjak@gmail.com"
                        }""")
        ).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testConfirmEmailBlankToken() throws Exception {
        mockMvc.perform(post("/api/v1/users/confirm-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "token": ""
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.token", hasItem("Token can't be blank")));
    }

    @Test
    public void testConfirmEmailNoToken() throws Exception {
        mockMvc.perform(post("/api/v1/users/confirm-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.token", hasItem("Token can't be null")));
    }

    @Test
    public void testConfirmEmailSuccess() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("lvrnjak1@etf.unsa.ba",
                "Password1#",
                "Password1#",
                "Lamija",
                "Vrnjak");
        User user = userService.create(createUserRequest);
        Token token = tokenService.generateToken(user, Token.TokenType.ACTIVATE_ACCOUNT);

        mockMvc.perform(post("/api/v1/users/confirm-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "token": "%s"
                        }""", token.getToken())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", is("Account successfully activated")));
    }

    @Test
    public void testConfirmEmailAlreadyActive() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("lvrnjak1@etf.unsa.ba",
                "Password1#",
                "Password1#",
                "Lamija",
                "Vrnjak");
        User user = userService.create(createUserRequest);
        Token token = tokenService.generateToken(user, Token.TokenType.ACTIVATE_ACCOUNT);
        user.setEnabled(true);
        userRepository.save(user);

        mockMvc.perform(post("/api/v1/users/confirm-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "token": "%s"
                        }""", token.getToken())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("User already confirmed email")));
    }

    @Test
    public void testConfirmEmailTokenNotFound() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("lvrnjak1@etf.unsa.ba",
                "Password1#",
                "Password1#",
                "Lamija",
                "Vrnjak");
        User user = userService.create(createUserRequest);
        tokenService.generateToken(user, Token.TokenType.ACTIVATE_ACCOUNT);

        mockMvc.perform(post("/api/v1/users/confirm-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "token": "%s"
                        }""", "invalid-token")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Invalid token")));
    }

    @Test
    public void testConfirmEmailInvalidToken() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("lvrnjak1@etf.unsa.ba",
                "Password1#",
                "Password1#",
                "Lamija",
                "Vrnjak");
        User user = userService.create(createUserRequest);
        Token token = tokenService.generateToken(user, Token.TokenType.ACTIVATE_ACCOUNT);
        token.setValid(false);
        tokenRepository.save(token);

        mockMvc.perform(post("/api/v1/users/confirm-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "token": "%s"
                        }""", token.getToken())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("Invalid token")));
    }

    @Test
    public void testResetPasswordInvalidPasswords() throws Exception {
        String[] errors = {
                "Password must contain at least one lowercase, one uppercase, one digit and one special character",
                "Password doesn't much confirmation",
                "Password must contain at least eight characters"
        };
        mockMvc.perform(post("/api/v1/users/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "password": "paa",
                            "confirm_password": "paas",
                            "token": "122"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.password").value(containsInAnyOrder(errors)))
                .andExpect(jsonPath("$.errors.password").value(hasSize(3)));
    }

    @Test
    public void testResetPasswordNoToken() throws Exception {
        String[] errors = {
                "Token can't be null",
                "Token can't be blank"
        };
        mockMvc.perform(post("/api/v1/users/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "password": "Password1#",
                            "confirm_password": "Password1#"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.token").value(containsInAnyOrder(errors)))
                .andExpect(jsonPath("$.errors.token").value(hasSize(2)));
    }

    @Test
    public void testResetPasswordBlankToken() throws Exception {
        String error = "Token can't be blank";
        mockMvc.perform(post("/api/v1/users/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "password": "Password1#",
                            "confirm_password": "Password1#",
                            "token": ""
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.token").value(hasItem(error)))
                .andExpect(jsonPath("$.errors.token").value(hasSize(1)));
    }

    @Test
    public void testResetPasswordSuccess() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("lvrnjak1@etf.unsa.ba",
                "Password1#",
                "Password1#",
                "Lamija",
                "Vrnjak");
        User user = userService.create(createUserRequest);
        user.setEnabled(true);
        userRepository.save(user);
        Token token = tokenService.generateToken(user, Token.TokenType.RESET_PASSWORD);

        mockMvc.perform(post("/api/v1/users/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "password": "Password2#",
                            "confirm_password": "Password2#",
                            "token": "%s"
                        }""", token.getToken())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", is("Password successfully changed")));
    }

    @Test
    public void testResetPasswordEmailNotConfirmed() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("lvrnjak1@etf.unsa.ba",
                "Password1#",
                "Password1#",
                "Lamija",
                "Vrnjak");
        User user = userService.create(createUserRequest);
        userRepository.save(user);
        Token token = tokenService.generateToken(user, Token.TokenType.RESET_PASSWORD);

        mockMvc.perform(post("/api/v1/users/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "password": "Password2#",
                            "confirm_password": "Password2#",
                            "token": "%s"
                        }""", token.getToken())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("User email not confirmed")));
    }

    @Test
    public void testResetPasswordTokenNotFound() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("lvrnjak1@etf.unsa.ba",
                "Password1#",
                "Password1#",
                "Lamija",
                "Vrnjak");
        User user = userService.create(createUserRequest);
        userRepository.save(user);
        tokenService.generateToken(user, Token.TokenType.RESET_PASSWORD);

        mockMvc.perform(post("/api/v1/users/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "password": "Password2#",
                            "confirm_password": "Password2#",
                            "token": "%s"
                        }""", "invalid-token")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Invalid token")));
    }

    @Test
    public void testResetPasswordTokenInvalid() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("lvrnjak1@etf.unsa.ba",
                "Password1#",
                "Password1#",
                "Lamija",
                "Vrnjak");
        User user = userService.create(createUserRequest);
        userRepository.save(user);
        Token token = tokenService.generateToken(user, Token.TokenType.RESET_PASSWORD);
        token.setValid(false);
        tokenRepository.save(token);

        mockMvc.perform(post("/api/v1/users/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "password": "Password2#",
                            "confirm_password": "Password2#",
                            "token": "%s"
                        }""", token.getToken())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("Invalid token")));
    }

    private User createUserInDb(String email, String password, String firstName, String lastName, Boolean enabled) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(enabled);
        return userRepository.save(user);
    }
}
