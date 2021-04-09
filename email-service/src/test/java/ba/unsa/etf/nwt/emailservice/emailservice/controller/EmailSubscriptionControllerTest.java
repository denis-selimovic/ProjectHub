package ba.unsa.etf.nwt.emailservice.emailservice.controller;

import ba.unsa.etf.nwt.emailservice.emailservice.client.TaskServiceClient;
import ba.unsa.etf.nwt.emailservice.emailservice.client.service.TaskService;
import ba.unsa.etf.nwt.emailservice.emailservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.emailservice.emailservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.emailservice.emailservice.exception.base.BadRequestException;
import ba.unsa.etf.nwt.emailservice.emailservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.emailservice.emailservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailSubscription;
import ba.unsa.etf.nwt.emailservice.emailservice.repository.EmailConfigRepository;
import ba.unsa.etf.nwt.emailservice.emailservice.repository.EmailSubscriptionRepository;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmailSubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private EmailConfigRepository emailConfigRepository;
    @Autowired
    private EmailSubscriptionRepository emailSubscriptionRepository;
    @MockBean
    private TaskServiceClient taskServiceClient;
    @MockBean
    private TaskService taskService;

    private String token;

    @BeforeEach
    public void setUp() {
        emailSubscriptionRepository.deleteAll();
        emailConfigRepository.deleteAll();
        token = tokenGenerator.createAccessToken(ResourceOwnerInjector.clientId,
                ResourceOwnerInjector.id,
                ResourceOwnerInjector.email).getValue();
    }

    @Test
    public void createSubscriptionSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        Mockito.when(taskService.findTaskById(Mockito.anyString(), Mockito.any()))
                .thenReturn(null);
        EmailConfig config = addConfigToDb(ResourceOwnerInjector.id, "email@email.com");
        mockMvc.perform(post("/api/v1/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJSONSub(uuid))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.task_id", Matchers.is(uuid.toString())));
    }

    @Test
    public void createSubscriptionNoConfig() throws Exception {
        UUID uuid = UUID.randomUUID();
        Mockito.when(taskService.findTaskById(Mockito.anyString(), Mockito.any()))
                .thenReturn(null);
        mockMvc.perform(post("/api/v1/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJSONSub(uuid))
        ).andExpect(status().isNotFound());
    }

    @Test
    public void createSubscriptionWrongUUID() throws Exception {
        Mockito.when(taskService.findTaskById(Mockito.anyString(), Mockito.any()))
                .thenReturn(null);
        mockMvc.perform(post("/api/v1/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "task_id" : "wrong_uuid"
                        }
                    """
                )
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void createSubscriptionNoToken() throws Exception {
        UUID uuid = UUID.randomUUID();
        Mockito.when(taskService.findTaskById(Mockito.anyString(), Mockito.any()))
                .thenReturn(null);
        EmailConfig config = addConfigToDb(ResourceOwnerInjector.id, "email@email.com");
        mockMvc.perform(post("/api/v1/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJSONSub(uuid))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void createSubscriptionTaskService422() throws Exception {
        UUID uuid = UUID.randomUUID();
        EmailConfig config = addConfigToDb(ResourceOwnerInjector.id, "email@email.com");
        Mockito.when(taskService.findTaskById(Mockito.anyString(), Mockito.any()))
                .thenThrow(new UnprocessableEntityException("Error"));
        mockMvc.perform(post("/api/v1/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJSONSub(uuid))
        ).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createSubscriptionTaskService404() throws Exception {
        UUID uuid = UUID.randomUUID();
        EmailConfig config = addConfigToDb(ResourceOwnerInjector.id, "email@email.com");
        Mockito.when(taskService.findTaskById(Mockito.anyString(), Mockito.any()))
                .thenThrow(new NotFoundException("Error"));
        mockMvc.perform(post("/api/v1/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJSONSub(uuid))
        ).andExpect(status().isNotFound());
    }

    @Test
    public void createSubscriptionTaskService400() throws Exception {
        UUID uuid = UUID.randomUUID();
        EmailConfig config = addConfigToDb(ResourceOwnerInjector.id, "email@email.com");
        Mockito.when(taskService.findTaskById(Mockito.anyString(), Mockito.any()))
                .thenThrow(new BadRequestException("Error"));
        mockMvc.perform(post("/api/v1/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJSONSub(uuid))
        ).andExpect(status().isBadRequest());
    }

    private String createJSONSub(UUID uuid) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("task_id", uuid.toString());
        return jsonObject.toString();
    }

    private EmailConfig addConfigToDb(UUID userId, String email) {
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setEmail(email);
        emailConfig.setUserId(userId);
        return emailConfigRepository.save(emailConfig);
    }

    private EmailSubscription addSubscriptionToDb(UUID taskId, EmailConfig config) {
        EmailSubscription emailSubscription = new EmailSubscription();
        emailSubscription.setConfig(config);
        emailSubscription.setTask(taskId);
        return emailSubscriptionRepository.save(emailSubscription);
    }
}
