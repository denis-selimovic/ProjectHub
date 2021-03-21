package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.projectservice.projectservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TokenGenerator tokenGenerator;

    private String token;

    @BeforeEach
    public void setUp() {
        projectRepository.deleteAll();
        token = "Bearer " + tokenGenerator.createAccessToken(
                ResourceOwnerInjector.clientId,
                ResourceOwnerInjector.id,
                ResourceOwnerInjector.email)
                .getValue();
    }

    @Test
    public void createProjectValidationBlank() throws Exception {
        mockMvc.perform(post("/api/v1/projects/add")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": ""
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.name").value(hasItem("Project name can't be blank")));;
    }

    @Test
    public void createProjectValidationTooLong() throws Exception {
        mockMvc.perform(post("/api/v1/projects/add")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "projekatprojekatprojekatprojekatprojekatprojekatprojekatprojekat"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.name").value(hasItem("Project name can contain at most 50 characters")));
    }

    @Test
    public void addExistentProject() throws Exception {
        createProjectInDB(ResourceOwnerInjector.id);

        mockMvc.perform(post("/api/v1/projects/add")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Projekat 1"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("Request body can not be processed")));
    }

    @Test
    public void addProjectSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/projects/add")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Project name"
                        }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").hasJsonPath())
                .andExpect(jsonPath("$.data.name").hasJsonPath())
                .andExpect(jsonPath("$.data.ownerId").hasJsonPath())
                .andExpect(jsonPath("$.data.createdAt").hasJsonPath())
                .andExpect(jsonPath("$.data.updatedAt").hasJsonPath());
    }

    @Test
    public void testDeleteNonexistentProject() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/" + UUID.randomUUID())
                .header("Authorization", token))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("Request body can not be processed")));
    }

    @Test
    public void testDeleteForeignProject() throws Exception {
        Project project = createProjectInDB(UUID.randomUUID());

        mockMvc.perform(delete("/api/v1/projects/" + project.getId())
                .header("Authorization", token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("You don't have permission for this activity")));
    }

    @Test
    public void testSuccessfulDeletion() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        mockMvc.perform(delete("/api/v1/projects/" + project.getId())
                .header("Authorization", token))
                .andExpect(status().isOk());
    }

    private Project createProjectInDB(UUID ownerID) {
        Project project = new Project();
        project.setName("Projekat 1");
        project.setOwnerId(ownerID);
        return projectRepository.save(project);
    }
}
