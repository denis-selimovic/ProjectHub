package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.config.TokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IssueControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenGenerator tokenGenerator;

    @Test
    public void createIssueValidationsBlank() throws Exception {
        mockMvc.perform(post("/api/v1/issues")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "",
                            "description": "",
                            "project_id": "d1c18630-ba07-470d-aa81-592c554dbe62",
                            "priority_id": "d1c18630-ba07-470d-aa81-592c554dbe62",
                            "type_id": "6acd9c39-a245-4985-aee5-2b217e159336"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.name").value(hasItem("Issue name can't be blank")))
                .andExpect(jsonPath("$.errors.description").value(hasItem("Issue description can't be blank")));
    }

    @Test
    public void createIssueValidationsTooLong() throws Exception {
        String tooLong = "a".repeat(256);
        mockMvc.perform(post("/api/v1/issues")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "name": "%s",
                            "description": "%s",
                            "project_id": "d1c18630-ba07-470d-aa81-592c554dbe62",
                            "priority_id": "d1c18630-ba07-470d-aa81-592c554dbe62",
                            "type_id": "6acd9c39-a245-4985-aee5-2b217e159336"
                        }""", tooLong, tooLong)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.name").value(hasItem("Issue name can contain at most 50 characters")))
                .andExpect(jsonPath("$.errors.description")
                        .value(hasItem("Issue description can contain at most 255 characters")));
    }

    @Test
    public void createIssueValidationsNull() throws Exception {
        mockMvc.perform(post("/api/v1/issues")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.name").value(hasItem("Issue name can't be blank")))
                .andExpect(jsonPath("$.errors.description").value(hasItem("Issue description can't be blank")))
                .andExpect(jsonPath("$.errors.project_id").value(hasItem("Project id can't be null")))
                .andExpect(jsonPath("$.errors.priority_id").value(hasItem("Priority id can't be null")));
    }
}
