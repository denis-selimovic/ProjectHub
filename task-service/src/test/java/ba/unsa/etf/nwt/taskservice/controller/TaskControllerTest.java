package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.taskservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.taskservice.model.Comment;
import ba.unsa.etf.nwt.taskservice.model.Priority;
import ba.unsa.etf.nwt.taskservice.model.Status;
import ba.unsa.etf.nwt.taskservice.model.Task;
import ba.unsa.etf.nwt.taskservice.model.Type;
import ba.unsa.etf.nwt.taskservice.repository.CommentRepository;
import ba.unsa.etf.nwt.taskservice.repository.PriorityRepository;
import ba.unsa.etf.nwt.taskservice.repository.StatusRepository;
import ba.unsa.etf.nwt.taskservice.repository.TaskRepository;
import ba.unsa.etf.nwt.taskservice.repository.TypeRepository;
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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private Priority critical;
    private Type bug;
    private Status open;
    private String token;


    @BeforeEach
    public void setUpTest() {
        taskRepository.deleteAll();
        typeRepository.deleteAll();
        statusRepository.deleteAll();
        priorityRepository.deleteAll();

        critical = new Priority();
        critical.setPriorityType(Priority.PriorityType.CRITICAL);
        critical = priorityRepository.save(critical);

        bug = new Type();
        bug.setType(Type.TaskType.BUG);
        bug = typeRepository.save(bug);

        open = new Status();
        open.setStatus(Status.StatusType.OPEN);
        open = statusRepository.save(open);

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
        Task task = createTaskInDB();

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
    public void deleteTaskSuccess() throws Exception {
        Task task = createTaskInDB();

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
        Task task = createTaskInDB();
        for (int i = 0; i < 25; i++) {
            createCommentInDB(task, UUID.randomUUID());
        }

        mockMvc.perform(delete(String.format("/api/v1/tasks/%s", task.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", is("Task successfully deleted")));

        assertEquals(0, commentRepository.count());
    }

    private Task createTaskInDB() {
        Task task = new Task();
        task.setName("Task name");
        task.setDescription("Desc");
        task.setType(bug);
        task.setPriority(critical);
        task.setStatus(open);
        UUID projectId = UUID.randomUUID();
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
