package ba.unsa.etf.nwt.notificationservice.controller;


import ba.unsa.etf.nwt.notificationservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.notificationservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.notificationservice.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


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
    /*
    @Test
    public void createSubscriptionBlankTaskId() throws Exception {
        mockMvc.perform(post("/api/subscriptions")
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

        mockMvc.perform(post("/api/subscriptions")
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
        String id = UUID.randomUUID().toString();
        mockMvc.perform(post("/api/subscriptions")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "task_id": "%s"
                        }""", id)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").hasJsonPath())
                .andExpect(jsonPath("$.data.user_id").hasJsonPath())
                .andExpect(jsonPath("$.data.task_id").hasJsonPath())
                .andExpect(jsonPath("$.data.created_at").hasJsonPath())
                .andExpect(jsonPath("$.data.updated_at").hasJsonPath())
                .andExpect(jsonPath("$.data.user_id", is(ResourceOwnerInjector.id.toString())))
                .andExpect(jsonPath("$.data.task_id", is(id)));
    }

    @Test
    public void deleteSubscriptionSuccess() throws Exception {
        Subscription subscription = createSubscriptionInDb();
        mockMvc.perform(delete(String.format("/api/subscriptions/%s", subscription.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", is("Subscription successfully deleted")));
    }

    @Test
    public void deleteSubscriptionBadId() throws Exception {
        mockMvc.perform(delete(String.format("/api/subscriptions/%s", UUID.randomUUID().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnprocessableEntityException))
                .andExpect(jsonPath("$.errors.message").value(hasItem("Request body can not be processed. This subscription doesn't exist")));
    }

    private Subscription createSubscriptionInDb() {
        Subscription subscription = new Subscription();
        subscription.setUserId(ResourceOwnerInjector.id);
        subscription.setTaskId(UUID.randomUUID());
        return subscriptionRepository.save(subscription);
    }
    */
}
