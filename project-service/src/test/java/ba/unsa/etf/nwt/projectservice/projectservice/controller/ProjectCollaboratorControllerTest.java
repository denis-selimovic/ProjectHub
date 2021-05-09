package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.client.dto.UserDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.client.service.UserService;
import ba.unsa.etf.nwt.projectservice.projectservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.projectservice.projectservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectNotificationDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.projectservice.projectservice.messaging.publishers.ProjectNotificationPublisher;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectCollaboratorRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @Autowired
    private ProjectService projectService;
    @MockBean
    private UserService userService;
    @MockBean
    private ProjectNotificationPublisher publisher;

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
    public void addCollaboratorNonExistentProject() throws Exception {
        mockMvc.perform(post("/api/v1/projects/3d14cdab-7074-482e-a1db-55d7aa3253df/collaborators")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "collaborator_id": "%s"
                        }""", UUID.randomUUID())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Project not found")));
    }

    @Test
    public void addCollaboratorNotOwner() throws Exception {
        Project project = createProjectInDB(UUID.randomUUID());
        mockMvc.perform(post(String.format("/api/v1/projects/%s/collaborators", project.getId()))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "collaborator_id": "%s"
                        }""", UUID.randomUUID())))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("You don't have permission for this activity")));
    }

    @Test
    public void addExistentCollaborator() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        ProjectCollaborator collaborator = createCollaboratorInDB(project);
        mockMvc.perform(post(String.format("/api/v1/projects/%s/collaborators", project.getId()))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "collaborator_id": "%s"
                        }""", collaborator.getCollaboratorId())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("Collaborator already added to this project")));
    }

    @Test
    public void addCollaboratorSuccess() throws Exception {
        Mockito.doNothing().when(publisher).send(Mockito.any(ProjectNotificationDTO.class));
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        mockMvc.perform(post(String.format("/api/v1/projects/%s/collaborators", project.getId()))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "collaborator_id": "%s"
                        }""", UUID.randomUUID())))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteNonexistentCollaborator() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        mockMvc.perform(delete(String.format("/api/v1/projects/%s/collaborators/%s", project.getId(), UUID.randomUUID()))
                .header("Authorization", token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Collaborator not found")));
    }

    @Test
    public void deleteCollaboratorNotOwner() throws Exception {
        Project project = createProjectInDB(UUID.randomUUID());
        ProjectCollaborator collaborator = createCollaboratorInDB(project);
        mockMvc.perform(delete(String.format("/api/v1/projects/%s/collaborators/%s", project.getId(), collaborator.getCollaboratorId()))
                .header("Authorization", token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("You don't have permission for this activity")));
    }

    @Test
    public void deleteCollaboratorSuccess() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        ProjectCollaborator collaborator = createCollaboratorInDB(project);
        mockMvc.perform(delete(String.format("/api/v1/projects/%s/collaborators/%s", project.getId(), collaborator.getCollaboratorId()))
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", Matchers.is("Project collaborator successfully deleted")));
        assertEquals(0, projectCollaboratorRepository.count());
    }

    @Test
    public void deleteCollaboratorOnDeletedProject() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        ProjectCollaborator collaborator = createCollaboratorInDB(project);
        projectService.softDelete(project);
        mockMvc.perform(delete(String.format("/api/v1/projects/%s/collaborators/%s", project.getId(), collaborator.getCollaboratorId()))
                .header("Authorization", token))
                .andExpect(status().isNotFound());
        assertEquals(0, projectCollaboratorRepository.count());
    }

    @Test
    public void getCollaboratorsForProject() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        for (int i = 0; i < 10; i++) {
            createCollaboratorInDB(project);
        }

        mockMvc.perform(get(String.format("/api/v1/projects/%s/collaborators?page=0&size=5", project.getId()))
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata").hasJsonPath())
                .andExpect(jsonPath("$.metadata.page_number", is(0)))
                .andExpect(jsonPath("$.metadata.total_elements", is(10)))
                .andExpect(jsonPath("$.metadata.page_size", is(5)))
                .andExpect(jsonPath("$.metadata.has_next", is(true)))
                .andExpect(jsonPath("$.metadata.has_previous", is(false)))
                .andExpect(jsonPath("$.data", hasSize(5)));
    }

    @Test
    public void getCollaboratorsForProjectOwner() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        for (int i = 0; i < 10; i++) {
            createCollaboratorInDB(project);
        }

        mockMvc.perform(get(String.format("/api/v1/projects/%s/collaborators?page=1&size=5", project.getId()))
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata").hasJsonPath())
                .andExpect(jsonPath("$.metadata.page_number", is(1)))
                .andExpect(jsonPath("$.metadata.total_elements", is(10)))
                .andExpect(jsonPath("$.metadata.page_size", is(5)))
                .andExpect(jsonPath("$.metadata.has_next", is(false)))
                .andExpect(jsonPath("$.metadata.has_previous", is(true)))
                .andExpect(jsonPath("$.data", hasSize(5)));
    }

    @Test
    public void getCollaboratorsForProjectCollaborator() throws Exception {
        Project project = createProjectInDB(UUID.randomUUID());
        for (int i = 0; i < 9; i++) {
            createCollaboratorInDB(project);
        }
        createCollaboratorInDB(project, ResourceOwnerInjector.id);

        mockMvc.perform(get(String.format("/api/v1/projects/%s/collaborators?page=1&size=5", project.getId()))
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata").hasJsonPath())
                .andExpect(jsonPath("$.metadata.page_number", is(1)))
                .andExpect(jsonPath("$.metadata.total_elements", is(10)))
                .andExpect(jsonPath("$.metadata.page_size", is(5)))
                .andExpect(jsonPath("$.metadata.has_next", is(false)))
                .andExpect(jsonPath("$.metadata.has_previous", is(true)))
                .andExpect(jsonPath("$.data", hasSize(5)));
    }

    @Test
    public void getCollaboratorsForProjectNotOwnerNorCollaborator() throws Exception {
        Project project = createProjectInDB(UUID.randomUUID());
        createCollaboratorInDB(project);

        mockMvc.perform(get(String.format("/api/v1/projects/%s/collaborators?page=1&size=5", project.getId()))
                .header("Authorization", token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("You don't have permission for this activity")));
    }

    @Test
    public void getCollaboratorByIdNotFound() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        ProjectCollaborator collaborator = createCollaboratorInDB(project);
        Mockito.when(userService.getUserById(Mockito.any(), eq(collaborator.getCollaboratorId()))).thenThrow(new NotFoundException("Not found"));

        mockMvc.perform(get(String.format("/api/v1/projects/%s/collaborators/%s", project.getId(), collaborator.getCollaboratorId()))
                .header("Authorization", token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Not found")));

    }

    @Test
    public void getCollaboratorByIdForbidden() throws Exception {
        Project project = createProjectInDB(UUID.randomUUID());
        ProjectCollaborator collaborator = createCollaboratorInDB(project);

        UUID userId = UUID.randomUUID();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);

        Mockito.when(userService.getUserById(Mockito.any(), eq(userId))).thenThrow(new ForbiddenException("Forbidden"));

        mockMvc.perform(get(String.format("/api/v1/projects/%s/collaborators/%s", project.getId(), collaborator.getCollaboratorId()))
                .header("Authorization", token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("You don't have permission for this activity")));

    }

    @Test
    public void getCollaboratorByIdSuccess() throws Exception {
        Project project = createProjectInDB(ResourceOwnerInjector.id);
        ProjectCollaborator collaborator = createCollaboratorInDB(project);

        UUID collaboratorId = collaborator.getCollaboratorId();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(collaboratorId);
        userDTO.setEmail("ime.prezime@gmail.com");
        userDTO.setFirstName("Ime");
        userDTO.setLastName("Prezime");

        Mockito.when(userService.getUserById(Mockito.any(), eq(collaboratorId))).thenReturn(userDTO);

        mockMvc.perform(get(String.format("/api/v1/projects/%s/collaborators/%s", project.getId(), collaboratorId))
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").hasJsonPath())
                .andExpect(jsonPath("$.data.email").hasJsonPath())
                .andExpect(jsonPath("$.data.first_name").hasJsonPath())
                .andExpect(jsonPath("$.data.last_name").hasJsonPath())
                .andExpect(jsonPath("$.data.created_at").hasJsonPath())
                .andExpect(jsonPath("$.data.updated_at").hasJsonPath());

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

    private void createCollaboratorInDB(Project project, UUID id) {
        ProjectCollaborator collaborator = new ProjectCollaborator();
        collaborator.setProject(project);
        collaborator.setCollaboratorId(id);
        projectCollaboratorRepository.save(collaborator);
    }


}
