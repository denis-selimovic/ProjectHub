package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.taskservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.taskservice.model.Comment;
import ba.unsa.etf.nwt.taskservice.model.Priority;
import ba.unsa.etf.nwt.taskservice.model.Status;
import ba.unsa.etf.nwt.taskservice.model.Task;
import ba.unsa.etf.nwt.taskservice.model.Type;
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

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private IssueRepository issueRepository;

    private Priority critical;
    private Priority high;
    private Type bug;
    private Type spike;
    private Status open;
    private Status inProgress;
    private String token;


    @BeforeEach
    public void setUpTest() {
        commentRepository.deleteAll();
        issueRepository.deleteAll();
        taskRepository.deleteAll();
        typeRepository.deleteAll();
        statusRepository.deleteAll();
        priorityRepository.deleteAll();

        critical = new Priority();
        critical.setPriorityType(Priority.PriorityType.CRITICAL);
        critical = priorityRepository.save(critical);
        high = new Priority();
        high.setPriorityType(Priority.PriorityType.HIGH);
        high = priorityRepository.save(high);

        bug = new Type();
        bug.setType(Type.TaskType.BUG);
        bug = typeRepository.save(bug);
        spike = new Type();
        spike.setType(Type.TaskType.SPIKE);
        spike = typeRepository.save(spike);

        open = new Status();
        open.setStatus(Status.StatusType.OPEN);
        open = statusRepository.save(open);
        inProgress = new Status();
        inProgress.setStatus(Status.StatusType.IN_PROGRESS);
        inProgress = statusRepository.save(inProgress);

        token = "Bearer " + tokenGenerator.createAccessToken(
                ResourceOwnerInjector.clientId,
                ResourceOwnerInjector.id,
                ResourceOwnerInjector.clientId
        ).getValue();
    }

    @Test
    public void createTaskSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "name": "Task microservice crud",
                            "description": "This is a description",
                            "project_id": "%s",
                            "priority_id": "%s",
                            "type_id": "%s"
                        }""", UUID.randomUUID(), critical.getId(), bug.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").hasJsonPath())
                .andExpect(jsonPath("$.data.name").hasJsonPath())
                .andExpect(jsonPath("$.data.description").hasJsonPath())
                .andExpect(jsonPath("$.data.user_id").hasJsonPath())
                .andExpect(jsonPath("$.data.project_id").hasJsonPath())
                .andExpect(jsonPath("$.data.status").hasJsonPath())
                .andExpect(jsonPath("$.data.type").hasJsonPath())
                .andExpect(jsonPath("$.data.priority").hasJsonPath())
                .andExpect(jsonPath("$.data.created_at").hasJsonPath())
                .andExpect(jsonPath("$.data.updated_at").hasJsonPath())
                .andExpect(jsonPath("$.data.status.id").value(is(open.getId().toString())));
    }

    @Test
    public void createTaskValidationsBlank() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
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
                .andExpect(jsonPath("$.errors.name").value(hasItem("Task name can't be blank")))
                .andExpect(jsonPath("$.errors.description").value(hasItem("Task description can't be blank")));
    }

    @Test
    public void createTaskValidationsTooLong() throws Exception {
        String tooLong = "a".repeat(256);
        mockMvc.perform(post("/api/v1/tasks")
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
                .andExpect(jsonPath("$.errors.name").value(hasItem("Task name can contain at most 50 characters")))
                .andExpect(jsonPath("$.errors.description")
                        .value(hasItem("Task description can contain at most 255 characters")));
    }

    @Test
    public void createTaskValidationsNull() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.name").value(hasItem("Task name can't be blank")))
                .andExpect(jsonPath("$.errors.description").value(hasItem("Task description can't be blank")))
                .andExpect(jsonPath("$.errors.project_id").value(hasItem("Project id can't be null")))
                .andExpect(jsonPath("$.errors.type_id").value(hasItem("Type id can't be null")))
                .andExpect(jsonPath("$.errors.priority_id").value(hasItem("Priority id can't be null")));
    }

    @Test
    public void createTaskSameName() throws Exception {
        Task task = createTaskInDB(UUID.randomUUID(), critical, open, bug);

        mockMvc.perform(post("/api/v1/tasks")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "name": "Task name",
                            "description": "This is a description",
                            "project_id": "%s",
                            "priority_id": "%s",
                            "type_id": "%s"
                        }""", task.getProjectId(), critical.getId(), bug.getId())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message").value(hasItem("Task with this name already exists")));
    }

    @Test
    public void testDefaultPaginationWithCriticalTasks() throws Exception {
        UUID projectId = UUID.randomUUID();
        for (int i = 0; i < 10; ++i) createTaskInDB(projectId, critical, open, bug);
        for (int i = 0; i < 10; ++i) createTaskInDB(projectId, critical, inProgress, bug);
        var result = mockMvc.perform(get(String.format("/api/v1/tasks?project_id=%s&priority_id=%s", projectId, critical.getId().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 20);
        for(int i = 0; i < data.length(); ++i) {
            assertEquals(data.getJSONObject(i).getJSONObject("priority").get("priority_type"), "CRITICAL");
            assertEquals(data.getJSONObject(i).get("project_id"), projectId.toString());
        }
    }

    @Test
    public void testDefaultPaginationWithCriticalAndInProgressTasks() throws Exception {
        UUID projectId = UUID.randomUUID();
        for (int i = 0; i < 10; ++i) createTaskInDB(projectId, critical, open, bug);
        for (int i = 0; i < 10; ++i) createTaskInDB(projectId, critical, inProgress, bug);
        var result = mockMvc.perform(get(String.format("/api/v1/tasks?project_id=%s&priority_id=%s&status_id=%s",
                projectId, critical.getId().toString(), inProgress.getId().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 10);
        for(int i = 0; i < data.length(); ++i) {
            assertEquals(data.getJSONObject(i).getJSONObject("priority").get("priority_type"), "CRITICAL");
            assertEquals(data.getJSONObject(i).getJSONObject("status").get("status"), "IN_PROGRESS");
            assertEquals(data.getJSONObject(i).get("project_id"), projectId.toString());
        }
    }

    @Test
    public void testDefaultPaginationWithHighPriorityAndSpikeTasks() throws Exception {
        UUID projectId = UUID.randomUUID();
        for (int i = 0; i < 10; ++i) createTaskInDB(projectId, critical, open, bug);
        for (int i = 0; i < 10; ++i) createTaskInDB(projectId, high, inProgress, spike);
        var result = mockMvc.perform(get(String.format("/api/v1/tasks?project_id=%s&priority_id=%s&type_id=%s",
                projectId, high.getId().toString(), spike.getId().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 10);
        for(int i = 0; i < data.length(); ++i) {
            assertEquals(data.getJSONObject(i).getJSONObject("priority").get("priority_type"), "HIGH");
            assertEquals(data.getJSONObject(i).getJSONObject("type").get("type"), "SPIKE");
            assertEquals(data.getJSONObject(i).get("project_id"), projectId.toString());
        }
    }

    @Test
    public void testPaginationNoResults() throws Exception {
        UUID projectId = UUID.randomUUID();
        for (int i = 0; i < 10; ++i) createTaskInDB(projectId, critical, open, bug);
        for (int i = 0; i < 10; ++i) createTaskInDB(projectId, high, inProgress, spike);
        var result = mockMvc.perform(get(String.format("/api/v1/tasks?project_id=%s&priority_id=%s&type_id=%s",
                projectId, critical.getId().toString(), spike.getId().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 0);
    }

    @Test
    public void deleteTaskSuccess() throws Exception {
        Task task = createTaskInDB(UUID.randomUUID(), critical, open, bug);

        mockMvc.perform(delete(String.format("/api/v1/tasks/%s", task.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTaskNotFound() throws Exception {
        mockMvc.perform(delete(String.format("/api/v1/tasks/%s", UUID.randomUUID()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Task not found")));
    }

    @Test
    public void deleteTaskWithComments() throws Exception {
        Task task = createTaskInDB(UUID.randomUUID(), critical, open, bug);
        for (int i = 0; i < 25; i++) {
            createCommentInDB(task, UUID.randomUUID());
        }

        mockMvc.perform(delete(String.format("/api/v1/tasks/%s", task.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", is("Task successfully deleted")));

        assertEquals(0, commentRepository.count());
    }

    private Task createTaskInDB(UUID projectId, Priority priority, Status status, Type type) {
        Task task = new Task();
        task.setName("Task name");
        task.setDescription("Desc");
        task.setType(type);
        task.setPriority(priority);
        task.setStatus(status);
        task.setProjectId(projectId);
        return taskRepository.save(task);
    }

    private Comment createCommentInDB(Task task, UUID userId) {
        Comment comment = new Comment();
        comment.setText("Comment");
        comment.setUserId(userId);
        comment.setTask(task);
        return commentRepository.save(comment);
    }
}
