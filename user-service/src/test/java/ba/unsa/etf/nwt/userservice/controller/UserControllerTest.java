package ba.unsa.etf.nwt.userservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

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
}
