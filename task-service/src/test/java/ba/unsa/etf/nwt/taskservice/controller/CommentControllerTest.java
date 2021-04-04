package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.client.dto.ProjectDTO;
import ba.unsa.etf.nwt.taskservice.client.service.ProjectService;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private TypeRepository typeRepository;
    @MockBean
    private ProjectService projectService;

    private Priority critical;
    private Type bug;
    private Status open;
    private String token;

    @BeforeEach
    private void setUpTest() {
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
    public void createCommentValidationsBlank() throws Exception {
        mockMvc.perform(post("/api/v1/tasks/d1c18630-ba07-470d-aa81-592c554dbe62/comments")
                .header(HttpHeaders.AUTHORIZATION, token)
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
                .header(HttpHeaders.AUTHORIZATION, token)
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
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.text").value(hasItem("Comment can't be blank")));
    }

    @Test
    public void createCommentTaskNotFound() throws Exception {
        mockMvc.perform(post("/api/v1/tasks/d1c18630-ba07-470d-aa81-592c554dbe62/comments")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "text": "This is a comment"
                        }"""))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Task not found")));
    }

    @Test
    public void createCommentSuccess() throws Exception {
        Task task = new Task();
        task.setName("Task name");
        task.setDescription("Desc");
        task.setType(bug);
        task.setPriority(critical);
        task.setStatus(open);

        UUID projectId = UUID.randomUUID();
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(projectId);
        Mockito.when(projectService.findProjectById(Mockito.any(), eq(projectId))).thenReturn(projectDTO);

        task.setProjectId(projectId);
        task = taskRepository.save(task);

        mockMvc.perform(post(String.format("/api/v1/tasks/%s/comments", task.getId()))
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "text": "This is a comment"
                        }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").hasJsonPath())
                .andExpect(jsonPath("$.data.text").hasJsonPath())
                .andExpect(jsonPath("$.data.user_id").hasJsonPath())
                .andExpect(jsonPath("$.data.created_at").hasJsonPath())
                .andExpect(jsonPath("$.data.updated_at").hasJsonPath())
                .andExpect(jsonPath("$.data.user_id", is(ResourceOwnerInjector.id.toString())))
                .andExpect(jsonPath("$.data.text", is("This is a comment")));
    }

    @Test
    public void deleteCommentNotAuthor() throws Exception {
        Task task = createTaskInDB();
        Comment comment = createCommentInDB(task, UUID.randomUUID());
        mockMvc.perform(delete(String.format("/api/v1/tasks/%s/comments/%s", task.getId(), comment.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("You don't have permission for this action")));
    }

    @Test
    public void deleteCommentSuccess() throws Exception {
        Task task = createTaskInDB();
        Comment comment = createCommentInDB(task, ResourceOwnerInjector.id);
        mockMvc.perform(delete(String.format("/api/v1/tasks/%s/comments/%s", task.getId(), comment.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", is("Comment successfully deleted")));
    }

    @Test
    public void getCommentsForTask1() throws Exception {
        Task task = createTaskInDB();
        for (int i = 0; i < 10; i++) {
            createCommentInDB(task, UUID.randomUUID());
        }

        mockMvc.perform(get(String.format("/api/v1/tasks/%s/comments?page=0&size=5", task.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
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
    public void getCommentsForTask2() throws Exception {
        Task task = createTaskInDB();
        for (int i = 0; i < 10; i++) {
            createCommentInDB(task, UUID.randomUUID());
        }

        mockMvc.perform(get(String.format("/api/v1/tasks/%s/comments?page=1&size=5", task.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
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
    public void getCommentsForTask3() throws Exception {
        Task task = createTaskInDB();
        for (int i = 0; i < 15; i++) {
            createCommentInDB(task, UUID.randomUUID());
        }

        mockMvc.perform(get(String.format("/api/v1/tasks/%s/comments", task.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata").hasJsonPath())
                .andExpect(jsonPath("$.metadata.page_number", is(0)))
                .andExpect(jsonPath("$.metadata.total_elements", is(15)))
                .andExpect(jsonPath("$.metadata.page_size", is(15)))
                .andExpect(jsonPath("$.metadata.has_next", is(false)))
                .andExpect(jsonPath("$.metadata.has_previous", is(false)))
                .andExpect(jsonPath("$.data", hasSize(15)));
    }

    @Test
    public void getCommentsForTask4() throws Exception {
        Task task = createTaskInDB();
        for (int i = 0; i < 25; i++) {
            createCommentInDB(task, UUID.randomUUID());
        }

        mockMvc.perform(get(String.format("/api/v1/tasks/%s/comments?page=1&size=15", task.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata").hasJsonPath())
                .andExpect(jsonPath("$.metadata.page_number", is(1)))
                .andExpect(jsonPath("$.metadata.total_elements", is(25)))
                .andExpect(jsonPath("$.metadata.page_size", is(10)))
                .andExpect(jsonPath("$.metadata.has_next", is(false)))
                .andExpect(jsonPath("$.metadata.has_previous", is(true)))
                .andExpect(jsonPath("$.data", hasSize(10)));
    }

    public Task createTaskInDB() {
        Task task = new Task();
        task.setName("Task name");
        task.setDescription("Desc");
        task.setType(bug);
        task.setPriority(critical);
        task.setStatus(open);

        UUID projectId = UUID.randomUUID();
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(projectId);
        Mockito.when(projectService.findProjectById(Mockito.any(), eq(projectId))).thenReturn(projectDTO);

        task.setProjectId(projectId);
        return taskRepository.save(task);
    }

//    private Task createTaskInDB() {
//        Task task = new Task();
//        task.setName("Task name");
//        task.setDescription("Desc");
//        task.setType(bug);
//        task.setPriority(critical);
//        task.setStatus(open);
//        UUID projectId = UUID.randomUUID();
//        task.setProjectId(projectId);
//        return taskRepository.save(task);
//    }

    private Comment createCommentInDB(Task task, UUID userId) {
        Comment comment = new Comment();
        comment.setText("Comment");
        comment.setUserId(userId);
        comment.setTask(task);
        return commentRepository.save(comment);
    }
}
