package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.config.TokenGenerator;
import ba.unsa.etf.nwt.taskservice.repository.PriorityRepository;
import ba.unsa.etf.nwt.taskservice.repository.StatusRepository;
import ba.unsa.etf.nwt.taskservice.repository.TaskRepository;
import ba.unsa.etf.nwt.taskservice.repository.TypeRepository;
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

    @BeforeEach
    public void setUpTest() {
        taskRepository.deleteAll();
    }

//    @Test
//    public void createTaskSuccess() throws Exception {
//        Priority critical = new Priority();
//        critical.setPriorityType(Priority.PriorityType.CRITICAL);
//        critical = priorityRepository.save(critical);
//
//        Type bug = new Type();
//        bug.setType(Type.TaskType.BUG);
//        bug = typeRepository.save(bug);
//
//        Status open = new Status();
//        open.setStatus(Status.StatusType.OPEN);
//        open = statusRepository.save(open);
//
//        mockMvc.perform(post("/api/v1/tasks")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenGenerator.createAccessToken(
//                        "client",
//                        UUID.randomUUID(),
//                        "email@email.com"
//                ).getValue())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(String.format("""
//                        {
//                            "name": "Task microservice crud",
//                            "description": "This is a description",
//                            "project_id": "%s",
//                            "priority_id": "%s",
//                            "type_id": "%s"
//                        }""", UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.id").hasJsonPath())
//                .andExpect(jsonPath("$.data.name").hasJsonPath())
//                .andExpect(jsonPath("$.data.description").hasJsonPath())
//                .andExpect(jsonPath("$.data.user_id").hasJsonPath())
//                .andExpect(jsonPath("$.data.project_id").hasJsonPath())
//                .andExpect(jsonPath("$.data.status_id").hasJsonPath())
//                .andExpect(jsonPath("$.data.type_id").hasJsonPath())
//                .andExpect(jsonPath("$.data.created_at").hasJsonPath())
//                .andExpect(jsonPath("$.data.updated_at").hasJsonPath())
//                .andExpect(jsonPath("$.data.name", is("Task microservice crud")))
//                .andExpect(jsonPath("$.data.description", is("This is a description")));
//    }

    @Test
    public void createTaskValidationsBlank() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue())
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
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue())
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
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.name").value(hasItem("Task name can't be blank")))
                .andExpect(jsonPath("$.errors.description").value(hasItem("Task description can't be blank")))
                .andExpect(jsonPath("$.errors.project_id").value(hasItem("Project id can't be null")))
                .andExpect(jsonPath("$.errors.type_id").value(hasItem("Type id can't be null")))
                .andExpect(jsonPath("$.errors.priority_id").value(hasItem("Priority id can't be null")));
    }
}
