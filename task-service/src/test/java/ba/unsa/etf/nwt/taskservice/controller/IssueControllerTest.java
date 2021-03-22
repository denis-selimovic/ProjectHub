package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.taskservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.taskservice.model.Issue;
import ba.unsa.etf.nwt.taskservice.model.Priority;
import ba.unsa.etf.nwt.taskservice.repository.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CommentRepository commentRepository;

    private Priority critical;
    private Priority medium;
    private String token;

    @BeforeEach
    public void setUpTest() {
        commentRepository.deleteAll();
        taskRepository.deleteAll();
        issueRepository.deleteAll();
        typeRepository.deleteAll();
        statusRepository.deleteAll();
        priorityRepository.deleteAll();

        critical = new Priority();
        critical.setPriorityType(Priority.PriorityType.CRITICAL);
        critical = priorityRepository.save(critical);

        medium = new Priority();
        medium.setPriorityType(Priority.PriorityType.MEDIUM);
        medium = priorityRepository.save(medium);

        token = "Bearer " + tokenGenerator.createAccessToken(
                ResourceOwnerInjector.clientId,
                ResourceOwnerInjector.id,
                ResourceOwnerInjector.clientId
        ).getValue();
    }

    @Test
    public void createIssueValidationsBlank() throws Exception {
        mockMvc.perform(post("/api/v1/issues")
                .header(HttpHeaders.AUTHORIZATION, token)
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
                .header(HttpHeaders.AUTHORIZATION, token)
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
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.name").value(hasItem("Issue name can't be blank")))
                .andExpect(jsonPath("$.errors.description").value(hasItem("Issue description can't be blank")))
                .andExpect(jsonPath("$.errors.project_id").value(hasItem("Project id can't be null")))
                .andExpect(jsonPath("$.errors.priority_id").value(hasItem("Priority id can't be null")));
    }

    @Test
    public void createIssueSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/issues")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "name": "This is an issue",
                            "description": "This is a description",
                            "project_id": "%s",
                            "priority_id": "%s"
                        }""", UUID.randomUUID(), critical.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").hasJsonPath())
                .andExpect(jsonPath("$.data.name").hasJsonPath())
                .andExpect(jsonPath("$.data.description").hasJsonPath())
                .andExpect(jsonPath("$.data.project_id").hasJsonPath())
                .andExpect(jsonPath("$.data.priority").hasJsonPath())
                .andExpect(jsonPath("$.data.created_at").hasJsonPath())
                .andExpect(jsonPath("$.data.updated_at").hasJsonPath());
    }

    @Test
    public void createIssueSameName() throws Exception {
        Issue issue = new Issue();
        issue.setName("Issue name");
        issue.setDescription("Desc");
        issue.setPriority(critical);
        UUID projectId = UUID.randomUUID();
        issue.setProjectId(projectId);
        issueRepository.save(issue);

        mockMvc.perform(post("/api/v1/issues")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "name": "Issue name",
                            "description": "This is a description",
                            "project_id": "%s",
                            "priority_id": "%s"
                        }""", projectId, critical.getId())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message").value(hasItem("Issue with this name already exists")));
    }

    @Test
    public void deleteIssueSuccess() throws Exception {
        Issue issue = new Issue();
        issue.setName("Issue name");
        issue.setDescription("Desc");
        issue.setPriority(critical);
        UUID projectId = UUID.randomUUID();
        issue.setProjectId(projectId);
        issue = issueRepository.save(issue);

        mockMvc.perform(delete(String.format("/api/v1/issues/%s", issue.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
    }

    @Test
    public void testIssueFiltersWithPaginationAndCriticalPriority() throws Exception {
        UUID projectId = UUID.randomUUID();
        for (int i = 0; i < 20; ++i) createIssueInDb(medium, projectId);
        for (int i = 0; i < 20; ++i) createIssueInDb(critical, projectId);
        var result = mockMvc.perform(get(String.format("/api/v1/issues?page=2&size=5&project_id=%s&priority_id=%s", projectId, critical.getId().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 5);
        for(int i = 0; i < data.length(); ++i) {
            assertEquals(data.getJSONObject(i).getJSONObject("priority").get("priority_type"), "CRITICAL");
            assertEquals(data.getJSONObject(i).get("project_id"), projectId.toString());
        }
    }

    @Test
    public void testIssueFiltersWithPaginationAndMediumPriority() throws Exception {
        UUID projectId = UUID.randomUUID();
        for (int i = 0; i < 20; ++i) createIssueInDb(medium, projectId);
        for (int i = 0; i < 20; ++i) createIssueInDb(critical, projectId);
        var result = mockMvc.perform(get(String.format("/api/v1/issues?page=1&size=7&project_id=%s&priority_id=%s", projectId, medium.getId().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 7);
        for(int i = 0; i < data.length(); ++i) {
            assertEquals(data.getJSONObject(i).getJSONObject("priority").get("priority_type"), "MEDIUM");
            assertEquals(data.getJSONObject(i).get("project_id"), projectId.toString());
        }
    }

    @Test
    public void testIssueFiltersWithDefaultPagination() throws Exception {
        UUID projectId = UUID.randomUUID();
        for (int i = 0; i < 20; ++i) createIssueInDb(medium, projectId);
        for (int i = 0; i < 20; ++i) createIssueInDb(critical, projectId);
        var result = mockMvc.perform(get(String.format("/api/v1/issues?project_id=%s&priority_id=%s", projectId, medium.getId().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 20);
        for(int i = 0; i < data.length(); ++i) {
            assertEquals(data.getJSONObject(i).getJSONObject("priority").get("priority_type"), "MEDIUM");
            assertEquals(data.getJSONObject(i).get("project_id"), projectId.toString());
        }
    }


    @Test
    public void deleteIssueNotFound() throws Exception {
        mockMvc.perform(delete(String.format("/api/v1/issues/%s", UUID.randomUUID()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Issue not found")));
    }

    private Issue createIssueInDb(Priority priority, UUID projectId) {
        Issue issue = new Issue();
        issue.setName("Name 1");
        issue.setDescription("Desc");
        issue.setPriority(priority);
        issue.setProjectId(projectId);
        return issueRepository.save(issue);
    }
}
