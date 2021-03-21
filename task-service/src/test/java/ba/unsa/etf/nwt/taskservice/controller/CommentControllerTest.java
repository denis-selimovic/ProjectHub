package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.config.TokenGenerator;
import ba.unsa.etf.nwt.taskservice.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    private void setUpTest() {
//        taskRepository.deleteAll();
//        Task task = new Task();
//        task.setName("Task");
//        task.setDescription("Description");
//        task.setProjectId(UUID.randomUUID());
//        task.setType();
//        task.setPriority();
//        task.setStatus();
//        taskRepository.save()
    }

    @Test
    public void createCommentValidationsBlank() throws Exception {
        mockMvc.perform(post("/api/v1/tasks/d1c18630-ba07-470d-aa81-592c554dbe62/comments")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "text": ""
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.text").value(hasItem("Comment can't be blank")));
    }

    @Test
    public void createCommentValidationsTooLong() throws Exception {
        String tooLong = "a".repeat(256);
        mockMvc.perform(post("/api/v1/tasks/d1c18630-ba07-470d-aa81-592c554dbe62/comments")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "text": "%s"
                        }""", tooLong)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.text")
                        .value(hasItem("Comment can contain at most 255 characters")));
    }

    @Test
    public void createCommentValidationsNull() throws Exception {
        mockMvc.perform(post("/api/v1/tasks/d1c18630-ba07-470d-aa81-592c554dbe62/comments")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.text").value(hasItem("Comment can't be blank")));
    }

    @Test
    public void createCommentTaskNotFound() throws Exception {
        mockMvc.perform(post("/api/v1/tasks/d1c18630-ba07-470d-aa81-592c554dbe62/comments")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "text": "This is a comment"
                        }"""))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Request can't be processed")));
    }
}
