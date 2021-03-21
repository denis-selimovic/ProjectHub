package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.projectservice.projectservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectCollaboratorRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private ProjectCollaboratorRepository projectCollaboratorRepository;
    @Autowired
    private TokenGenerator tokenGenerator;

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
    public void createProjectValidationBlank() throws Exception {
        mockMvc.perform(post("/api/v1/projects")
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
        mockMvc.perform(post("/api/v1/projects")
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

        mockMvc.perform(post("/api/v1/projects")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Projekat 1"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("Project with this name already exists")));
    }

    @Test
    public void addProjectSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/projects")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Project name"
                        }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").hasJsonPath())
                .andExpect(jsonPath("$.data.name").hasJsonPath())
                .andExpect(jsonPath("$.data.owner_id").hasJsonPath())
                .andExpect(jsonPath("$.data.created_at").hasJsonPath())
                .andExpect(jsonPath("$.data.updated_at").hasJsonPath());
    }

    @Test
    public void deleteNonexistentProject() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/" + UUID.randomUUID())
                .header("Authorization", token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Project not found")));
    }

    @Test
    public void deleteProjectNotOwner() throws Exception {
        Project project = createProjectInDB(UUID.randomUUID());

        mockMvc.perform(delete("/api/v1/projects/" + project.getId())
                .header("Authorization", token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("You don't have permission for this activity")));
    }

    @Test
    public void successfulDeletion() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        mockMvc.perform(delete("/api/v1/projects/" + project.getId())
                .header("Authorization", token))
                .andExpect(status().isOk());
        assertEquals(0, projectRepository.count());
    }

    @Test
    public void deleteProjectAndCollaborators() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        ProjectCollaborator collaborator1 = new ProjectCollaborator();
        collaborator1.setProject(project);
        collaborator1.setCollaboratorId(UUID.randomUUID());
        projectCollaboratorRepository.save(collaborator1);

        ProjectCollaborator collaborator2 = new ProjectCollaborator();
        collaborator2.setProject(project);
        collaborator2.setCollaboratorId(UUID.randomUUID());
        projectCollaboratorRepository.save(collaborator2);
        assertEquals(1, projectRepository.count());
        assertEquals(2, projectCollaboratorRepository.count());

        mockMvc.perform(delete("/api/v1/projects/" + project.getId())
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", Matchers.is("Project successfully deleted")));
        assertEquals(0, projectRepository.count());
        assertEquals(0, projectCollaboratorRepository.count());
    }

    private Project createProjectInDB(UUID ownerID) {
        Project project = new Project();
        project.setName("Projekat 1");
        project.setOwnerId(ownerID);
        return projectRepository.save(project);
    }
}
