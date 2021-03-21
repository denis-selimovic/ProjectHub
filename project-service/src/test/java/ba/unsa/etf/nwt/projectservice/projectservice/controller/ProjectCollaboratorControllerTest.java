package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.projectservice.projectservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectCollaboratorRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProjectCollaboratorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private ProjectCollaboratorRepository projectCollaboratorRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private String token;


    @BeforeEach
    public void setUp() {
        projectRepository.deleteAll();
        projectCollaboratorRepository.deleteAll();
        token = "Bearer " + tokenGenerator.createAccessToken(
                ResourceOwnerInjector.clientId,
                ResourceOwnerInjector.id,
                ResourceOwnerInjector.email)
                .getValue();
    }

    @Test
    public void addCollaboratorNotAuthor() throws Exception {
        Project project = createProjectInDB(UUID.randomUUID());
        mockMvc.perform(post("/api/v1/collaborators/add")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "collaborator_id": "%s",
                            "project_id": "%s"
                        }""", UUID.randomUUID(), project.getId())))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("You don't have permission for this activity")));
    }

    @Test
    public void addCollaboratorNonExistentProject() throws Exception {
        mockMvc.perform(post("/api/v1/collaborators/add")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "collaborator_id": "%s",
                            "project_id": "%s"
                        }""", UUID.randomUUID(), UUID.randomUUID())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("Request body can not be processed")));
    }

    @Test
    public void addCollaboratorSuccess() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        mockMvc.perform(post("/api/v1/collaborators/add")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "collaborator_id": "%s",
                            "project_id": "%s"
                        }""", UUID.randomUUID(), project.getId())))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteNonexistentCollaborator() throws Exception {
        mockMvc.perform(delete("/api/v1/collaborators/" + UUID.randomUUID())
                .header("Authorization", token))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("Request body can not be processed")));
    }

    @Test
    public void deleteCollaboratorNotAuthor() throws Exception {
        Project project = createProjectInDB(UUID.randomUUID());
        ProjectCollaborator collaborator = createCollaboratorInDB(project);
        mockMvc.perform(delete("/api/v1/collaborators/" + collaborator.getId())
                .header("Authorization", token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("You don't have permission for this activity")));
    }

    @Test
    public void deleteCollaboratorSuccess() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        ProjectCollaborator collaborator = createCollaboratorInDB(project);
        mockMvc.perform(delete("/api/v1/collaborators/" + collaborator.getId())
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", Matchers.is("Project collaborator successfully deleted")));
        assertEquals(0, projectCollaboratorRepository.count());
    }

    private Project createProjectInDB(UUID ownerID) {
        Project project = new Project();
        project.setName("Projekat 1");
        project.setOwnerId(ownerID);
        return projectRepository.save(project);
    }

    private ProjectCollaborator createCollaboratorInDB(Project project) {
        ProjectCollaborator collaborator = new ProjectCollaborator();
        collaborator.setProject(project);
        collaborator.setCollaboratorId(UUID.randomUUID());
        return projectCollaboratorRepository.save(collaborator);
    }


}
