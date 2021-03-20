package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.config.TokenGenerator;
import ba.unsa.etf.nwt.taskservice.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
//    @Autowired
//    private static StatusRepository statusRepository;
//    @Autowired
//    private static TypeRepository typeRepository;
//    @Autowired
//    private static PriorityRepository priorityRepository;
//
//    private static List<Priority> priorities = new ArrayList<>();
//    private static List<Status> statuses = new ArrayList<>();
//    private static List<Type> types = new ArrayList<>();

//    @BeforeAll
//    public static void setupClass() {
//        insertTypes();
//        insertPriorities();
//        insertStatuses();
//    }
//
//    private static  void insertTypes() {
//        Type bug = new Type();
//        bug.setType(Type.TaskType.BUG);
//        types.add(bug);
//        Type change = new Type();
//        change.setType(Type.TaskType.CHANGE);
//        types.add(change);
//        Type epic = new Type();
//        epic.setType(Type.TaskType.EPIC);
//        types.add(epic);
//        Type story = new Type();
//        story.setType(Type.TaskType.STORY);
//        types.add(story);
//        Type spike = new Type();
//        spike.setType(Type.TaskType.SPIKE);
//        types.add(spike);
//    }
//
//    private static void insertPriorities() {
//        Priority critical = new Priority();
//        critical.setPriorityType(Priority.PriorityType.CRITICAL);
//        priorities.add(critical);
//        Priority high = new Priority();
//        high.setPriorityType(Priority.PriorityType.HIGH);
//        priorities.add(high);
//        Priority medium = new Priority();
//        medium.setPriorityType(Priority.PriorityType.MEDIUM);
//        priorities.add(medium);
//        Priority low = new Priority();
//        low.setPriorityType(Priority.PriorityType.LOW);
//        priorities.add(low);
//    }
//
//    private static void insertStatuses() {
//        Status open = new Status();
//        open.setStatus(Status.StatusType.OPEN);
//        statuses.add(open);
//        Status inProgress = new Status();
//        inProgress.setStatus(Status.StatusType.IN_PROGRESS);
//        statuses.add(inProgress);
//        Status inReview = new Status();
//        inReview.setStatus(Status.StatusType.IN_REVIEW);
//        statuses.add(inReview);
//        Status inTesting = new Status();
//        inTesting.setStatus(Status.StatusType.IN_TESTING);
//        statuses.add(inTesting);
//        Status done = new Status();
//        done.setStatus(Status.StatusType.DONE);
//        statuses.add(done);
//    }
//
    @BeforeEach
    public void setUpTest() {
        taskRepository.deleteAll();
    }

//    @Test
//    public void createTaskSuccess() throws Exception {
//        mockMvc.perform(post("/api/v1/tasks")
//                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
//                        "client",
//                        UUID.randomUUID(),
//                        "email@email.com"
//                ).getValue())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("""
//                        {
//                            "name": "Task microservice crud",
//                            "description": "This is a description",
//                            "project_id": "d1c18630-ba07-470d-aa81-592c554dbe62",
//                            "priority_id": "d1c18630-ba07-470d-aa81-592c554dbe62",
//                            "type_id": "6acd9c39-a245-4985-aee5-2b217e159336"
//                        }"""))
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
    public void createTaskValidationsFail() throws Exception {
        String[] errors = {

        };
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
                .andExpect(status().isUnprocessableEntity());
    }
}
