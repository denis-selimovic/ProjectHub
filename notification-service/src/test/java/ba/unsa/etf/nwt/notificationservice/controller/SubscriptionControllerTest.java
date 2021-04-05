package ba.unsa.etf.nwt.notificationservice.controller;


import ba.unsa.etf.nwt.notificationservice.client.dto.TaskDTO;
import ba.unsa.etf.nwt.notificationservice.client.service.TaskService;
import ba.unsa.etf.nwt.notificationservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.notificationservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.notificationservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.notificationservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import ba.unsa.etf.nwt.notificationservice.repository.SubscriptionRepository;
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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SubscriptionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @MockBean
    private TaskService taskService;
    private String token;

    @BeforeEach
    public void setUp() {

        subscriptionRepository.deleteAll();

        token = "Bearer " + tokenGenerator.createAccessToken(
                ResourceOwnerInjector.clientId,
                ResourceOwnerInjector.id,
                ResourceOwnerInjector.clientId
        ).getValue();
    }

    @Test
    public void createSubscriptionBlankTaskId() throws Exception {
        mockMvc.perform(post("/api/v1/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "task_id": ""
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.task_id").value(hasItem("Task id can't be null")));
    }

    @Test
    public void subscriptionAlreadyExists() throws Exception {
        Subscription subscription = createSubscriptionInDb();
        UUID taskId = subscription.getTaskId();

        mockMvc.perform(post("/api/v1/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "task_id": "%s"
                        }""", taskId)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnprocessableEntityException))
                .andExpect(jsonPath("$.errors.message").value(hasItem("Request body can not be processed. You have already subscribed to the task.")));
    }

    @Test
    public void createSubscriptionSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(id);
        Mockito.when(taskService.findTaskById(Mockito.any(), eq(id))).thenReturn(taskDTO);
        mockMvc.perform(post("/api/v1/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "task_id": "%s"
                        }""", id.toString())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").hasJsonPath())
                .andExpect(jsonPath("$.data.user_id").hasJsonPath())
                .andExpect(jsonPath("$.data.task_id").hasJsonPath())
                .andExpect(jsonPath("$.data.created_at").hasJsonPath())
                .andExpect(jsonPath("$.data.updated_at").hasJsonPath())
                .andExpect(jsonPath("$.data.user_id", is(ResourceOwnerInjector.id.toString())))
                .andExpect(jsonPath("$.data.task_id", is(id.toString())));
    }

    @Test
    public void deleteSubscriptionSuccess() throws Exception {
        Subscription subscription = createSubscriptionInDb();
        mockMvc.perform(delete(String.format("/api/v1/subscriptions/%s", subscription.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", is("Subscription successfully deleted")));
    }

    @Test
    public void deleteSubscriptionBadId() throws Exception {
        mockMvc.perform(delete(String.format("/api/v1/subscriptions/%s", UUID.randomUUID().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message").value(hasItem("Subscription doesn't exist")));
    }

    @Test
    public void deleteSubscriptionNotOwner() throws Exception {
        Subscription subscription = createSubscriptionInDb(UUID.randomUUID());
        mockMvc.perform(delete(String.format("/api/v1/subscriptions/%s", subscription.getId().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message").value(hasItem("Subscription doesn't exist")));
    }

    @Test
    public void createSubscriptionNotFoundFromTaskMs() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(taskService.findTaskById(Mockito.any(), eq(id))).thenThrow(new NotFoundException("Not found"));
        mockMvc.perform(post("/api/v1/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "task_id": "%s"
                        }""", id.toString())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Not found")));
    }

    @Test
    public void createSubscriptionForbiddenFromTaskMs() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(taskService.findTaskById(Mockito.any(), eq(id))).thenThrow(new ForbiddenException("Forbidden"));
        mockMvc.perform(post("/api/v1/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "task_id": "%s"
                        }""", id.toString())))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.message", hasItem("Forbidden")));
    }

    private Subscription createSubscriptionInDb() {
        return createSubscriptionInDb(ResourceOwnerInjector.id);
    }

    private Subscription createSubscriptionInDb(UUID userId) {
        Subscription subscription = new Subscription();
        subscription.setUserId(userId);
        subscription.setTaskId(UUID.randomUUID());
        return subscriptionRepository.save(subscription);
    }
}
