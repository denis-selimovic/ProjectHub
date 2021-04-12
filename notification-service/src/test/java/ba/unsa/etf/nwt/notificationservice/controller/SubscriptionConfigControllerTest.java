package ba.unsa.etf.nwt.notificationservice.controller;

import ba.unsa.etf.nwt.notificationservice.client.service.TaskService;
import ba.unsa.etf.nwt.notificationservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.notificationservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.notificationservice.model.SubscriptionConfig;
import ba.unsa.etf.nwt.notificationservice.repository.SubscriptionConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SubscriptionConfigControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private SubscriptionConfigRepository subscriptionConfigRepository;
    @MockBean
    private TaskService taskService;
    private String token;

    @BeforeEach
    public void setUp() {
        subscriptionConfigRepository.deleteAll();

        token = "Bearer " + tokenGenerator.createAccessToken(
                ResourceOwnerInjector.clientId,
                ResourceOwnerInjector.id,
                ResourceOwnerInjector.clientId
        ).getValue();
    }

    @Test
    public void createConfigBlankEmail() throws Exception {
        mockMvc.perform(post("/api/v1/subscription-config")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "user_id": "%s",
                            "email": ""
                        }""", UUID.randomUUID())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.email").value(hasItem("Email can't be blank")));
    }

    @Test
    public void createConfigNoEmail() throws Exception {
        mockMvc.perform(post("/api/v1/subscription-config")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "user_id": "%s"
                        }""", UUID.randomUUID())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.email").value(hasItem("Email can't be blank")));
    }

    @Test
    public void createConfigNoUserId() throws Exception {
        mockMvc.perform(post("/api/v1/subscription-config")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "email": "%s"
                        }""", ResourceOwnerInjector.email)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.user_id").value(hasItem("User id can't be null")));
    }

    @Test
    public void createConfigSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/subscription-config")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "user_id": "%s",
                            "email": "%s"
                        }""", ResourceOwnerInjector.id, ResourceOwnerInjector.email)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.user_id").value(is(ResourceOwnerInjector.id.toString())))
                .andExpect(jsonPath("$.data.email").value(is(ResourceOwnerInjector.email)));
    }

    @Test
    public void createConfigEmailInUse() throws Exception {
        createSubscriptionConfigInDb(UUID.randomUUID(), ResourceOwnerInjector.email);
        mockMvc.perform(post("/api/v1/subscription-config")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "user_id": "%s",
                            "email": "%s"
                        }""", UUID.randomUUID(), ResourceOwnerInjector.email)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message").value(hasItem("Config with this email or user id already exists")));
    }

    @Test
    public void createConfigUserIdInUse() throws Exception {
        createSubscriptionConfigInDb(ResourceOwnerInjector.id, "lv@gmail.com");
        mockMvc.perform(post("/api/v1/subscription-config")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "user_id": "%s",
                            "email": "%s"
                        }""", ResourceOwnerInjector.id, ResourceOwnerInjector.email)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message").value(hasItem("Config with this email or user id already exists")));
    }

    private SubscriptionConfig createSubscriptionConfigInDb(UUID userId, String email) {
        SubscriptionConfig subscriptionConfig = new SubscriptionConfig();
        subscriptionConfig.setUserId(userId);
        subscriptionConfig.setEmail(email);
        return subscriptionConfigRepository.save(subscriptionConfig);
    }
}
