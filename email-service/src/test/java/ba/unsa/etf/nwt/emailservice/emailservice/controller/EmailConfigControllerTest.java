package ba.unsa.etf.nwt.emailservice.emailservice.controller;

import antlr.Token;
import ba.unsa.etf.nwt.emailservice.emailservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.emailservice.emailservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.repository.EmailConfigRepository;
import ba.unsa.etf.nwt.emailservice.emailservice.repository.EmailSubscriptionRepository;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmailConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private EmailConfigRepository emailConfigRepository;
    @Autowired
    private EmailSubscriptionRepository emailSubscriptionRepository;

    private String token;

    @BeforeEach
    public void setUp() {
        emailSubscriptionRepository.deleteAll();
        emailConfigRepository.deleteAll();
        token = tokenGenerator.createAccessToken(ResourceOwnerInjector.clientId,
                    ResourceOwnerInjector.id,
                    ResourceOwnerInjector.email)
                .getValue();
    }

    @Test
    public void createConfigSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(post("/api/v1/email-config")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJSONConfig(uuid, "email@email.com"))
        ).andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.email", Matchers.is("email@email.com")))
        .andExpect(jsonPath("$.data.user_id", Matchers.is(uuid.toString())));
    }

    @Test
    public void createConfigInvalidEmail() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(post("/api/v1/email-config")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJSONConfig(uuid, "email"))
        ).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createConfigUserExists() throws Exception {
        UUID uuid = UUID.randomUUID();
        EmailConfig config = addConfigToDb(uuid, "email@email.com");
        mockMvc.perform(post("/api/v1/email-config")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJSONConfig(uuid, "email@email.com"))
        ).andExpect(status().isUnprocessableEntity());
    }

    private String createJSONConfig(UUID userId, String email) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("user_id", userId.toString());
        json.put("email", email);
        return json.toString();
    }

    private EmailConfig addConfigToDb(UUID userId, String email) {
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setEmail(email);
        emailConfig.setUserId(userId);
        return emailConfigRepository.save(emailConfig);
    }
}
