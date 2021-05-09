package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.projectservice.projectservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectCollaboratorRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectService;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private ProjectService projectService;
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
                .andExpect(jsonPath("$.errors.name").value(hasItem("Project name can't be blank")));
        ;
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
        createProjectInDB(ResourceOwnerInjector.id, "Projekat 1");

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
        Project project = createProjectInDB(UUID.randomUUID(), "Projekat");

        mockMvc.perform(delete("/api/v1/projects/" + project.getId())
                .header("Authorization", token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("You don't have permission for this activity")));
    }

    @Test
    public void successfulDeletion() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id, "Projekat");
        mockMvc.perform(delete("/api/v1/projects/" + project.getId())
                .header("Authorization", token))
                .andExpect(status().isOk());
        assertEquals(0, projectRepository.count());
    }

    @Test
    public void deleteProjectAndCollaborators() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id, "Projekat");
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

    @Test
    public void getProjectsInvalidFilter() throws Exception {
        mockMvc.perform(get("/api/v1/projects?filter=invalid")
                .header("Authorization", token))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("Invalid filter")));
    }

    @Test
    public void testGetProjectsForOwner() throws Exception {
        UUID userId = ResourceOwnerInjector.id;
        createProjectInDB(userId, "P1");
        createProjectInDB(userId, "P2");
        createProjectInDB(userId, "P3");
        createProjectInDB(UUID.randomUUID(), "P4");

        var result = mockMvc.perform(get("/api/v1/projects?filter=owner")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata.page_number", Matchers.is(0)))
                .andExpect(jsonPath("$.metadata.total_elements", Matchers.is(3)))
                .andExpect(jsonPath("$.metadata.page_size", Matchers.is(3)))
                .andExpect(jsonPath("$.metadata.has_next", Matchers.is(false)))
                .andExpect(jsonPath("$.metadata.has_previous", Matchers.is(false)))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 3);
        for (int i = 0; i < data.length(); ++i) {
            assertEquals(data.getJSONObject(i).getString("owner_id"), userId.toString());
        }
    }

    @Test
    public void testGetProjectsForOwnerPagination() throws Exception {
        UUID userId = ResourceOwnerInjector.id;
        for (int i = 0; i < 10; i++) {
            createProjectInDB(userId, "P" + i);
        }
        createProjectInDB(UUID.randomUUID(), "P4");

        var result = mockMvc.perform(get("/api/v1/projects?filter=owner&page=1&size=4")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata.page_number", Matchers.is(1)))
                .andExpect(jsonPath("$.metadata.total_elements", Matchers.is(10)))
                .andExpect(jsonPath("$.metadata.page_size", Matchers.is(4)))
                .andExpect(jsonPath("$.metadata.has_next", Matchers.is(true)))
                .andExpect(jsonPath("$.metadata.has_previous", Matchers.is(true)))
                .andExpect(jsonPath("$.data", hasSize(4)))
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 4);
        for (int i = 0; i < data.length(); ++i) {
            assertEquals(data.getJSONObject(i).getString("owner_id"), userId.toString());
        }
    }

    @Test
    public void testGetProjectsForOwnerPagination2() throws Exception {
        UUID userId = ResourceOwnerInjector.id;
        for (int i = 0; i < 10; i++) {
            createProjectInDB(userId, "P" + i);
        }
        createProjectInDB(UUID.randomUUID(), "P4");

        mockMvc.perform(get("/api/v1/projects?filter=owner&page=2&size=4")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata.page_number", Matchers.is(2)))
                .andExpect(jsonPath("$.metadata.total_elements", Matchers.is(10)))
                .andExpect(jsonPath("$.metadata.page_size", Matchers.is(2)))
                .andExpect(jsonPath("$.metadata.has_next", Matchers.is(false)))
                .andExpect(jsonPath("$.metadata.has_previous", Matchers.is(true)))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    public void testGetProjectsForOwnerPagination3() throws Exception {
        UUID userId = ResourceOwnerInjector.id;
        Project p = createProjectInDB(userId, "P");
        projectService.softDelete(p);

        mockMvc.perform(get("/api/v1/projects?filter=owner&page=0&size=4")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata.page_number", Matchers.is(0)))
                .andExpect(jsonPath("$.metadata.total_elements", Matchers.is(0)))
                .andExpect(jsonPath("$.metadata.page_size", Matchers.is(0)))
                .andExpect(jsonPath("$.metadata.has_next", Matchers.is(false)))
                .andExpect(jsonPath("$.metadata.has_previous", Matchers.is(false)))
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andReturn();
    }

    @Test
    public void testGetProjectsForCollaborator() throws Exception {
        UUID userId = ResourceOwnerInjector.id;
        createProjectInDB(userId, "P1");
        createProjectInDB(userId, "P2");
        List<Project> projects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Project project = createProjectInDB(UUID.randomUUID(), "P" + i);
            createCollaboratorInDB(project, ResourceOwnerInjector.id);
            projects.add(project);
        }

        var result = mockMvc.perform(get("/api/v1/projects?filter=collaborator")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata.page_number", Matchers.is(0)))
                .andExpect(jsonPath("$.metadata.total_elements", Matchers.is(10)))
                .andExpect(jsonPath("$.metadata.page_size", Matchers.is(10)))
                .andExpect(jsonPath("$.metadata.has_next", Matchers.is(false)))
                .andExpect(jsonPath("$.metadata.has_previous", Matchers.is(false)))
                .andExpect(jsonPath("$.data", hasSize(10)))
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 10);
        for (int i = 0; i < data.length(); ++i) {
            assertNotEquals(data.getJSONObject(i).getString("owner_id"), userId.toString());
            var pc = projectCollaboratorRepository.findByCollaboratorIdAndProjectId(userId,
                    projects.get(i).getId()
            );
            assertTrue(pc.isPresent());
        }
    }

    @Test
    public void patchProjectNotFound() throws Exception {
        mockMvc.perform(patch(String.format("/api/v1/projects/%s", UUID.randomUUID()))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Project not found")));
    }

    @Test
    public void patchProjectChangeName() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id, "Project name");
        mockMvc.perform(patch(String.format("/api/v1/projects/%s", project.getId()))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "New project name"
                        }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("New project name")))
                .andExpect(jsonPath("$.data.owner_id").value(is(project.getOwnerId().toString())));
    }

    @Test
    public void patchProjectNotOwner() throws Exception {
        Project project = createProjectInDB(UUID.randomUUID(), "Project name");
        mockMvc.perform(patch(String.format("/api/v1/projects/%s", project.getId()))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "New project name"
                        }"""))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("You don't have permission for this activity")));
    }

    private Project createProjectInDB(UUID ownerID, final String name) {
        Project project = new Project();
        project.setName(name);
        project.setOwnerId(ownerID);
        return projectRepository.save(project);
    }

    private void createCollaboratorInDB(Project project, UUID id) {
        ProjectCollaborator collaborator = new ProjectCollaborator();
        collaborator.setProject(project);
        collaborator.setCollaboratorId(id);
        projectCollaboratorRepository.save(collaborator);
    }
}
