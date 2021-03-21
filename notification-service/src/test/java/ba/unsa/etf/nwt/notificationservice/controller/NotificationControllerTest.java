package ba.unsa.etf.nwt.notificationservice.controller;

import ba.unsa.etf.nwt.notificationservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.notificationservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationRepository;
import ba.unsa.etf.nwt.notificationservice.service.NotificationService;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    private String token;

    @BeforeEach
    public void setUp() {

        notificationRepository.deleteAll();

        token = "Bearer " + tokenGenerator.createAccessToken(
                ResourceOwnerInjector.clientId,
                ResourceOwnerInjector.id,
                ResourceOwnerInjector.clientId
        ).getValue();
    }

    @Test
    public void createNotificationBlankTitle() throws Exception {
        mockMvc.perform(post("/api/notifications")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "title": "",
                            "description": "Description"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.title").value(hasItem("Notification title can't be blank")));
    }

    @Test
    public void createNotificationBlankDescription() throws Exception {
        mockMvc.perform(post("/api/notifications")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "title": "Title",
                            "description": ""
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.description").value(hasItem("Notification description can't be blank")));
    }

    @Test
    public void createNotificationBlankTitleAndDescription() throws Exception {
        mockMvc.perform(post("/api/notifications")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "title": "",
                            "description": ""
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.title").value(hasItem("Notification title can't be blank")))
                .andExpect(jsonPath("$.errors.description").value(hasItem("Notification description can't be blank")));
    }

    @Test
    public void createNotificationTooLongTitle() throws Exception {
        String tooLongTitle = "t".repeat(51);
        mockMvc.perform(post("/api/notifications")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "title": "%s",
                            "description": "Description"
                        }""", tooLongTitle)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.title")
                        .value(hasItem("Notification title can have at most 50 characters")));
    }

    @Test
    public void createNotificationTooLongDescription() throws Exception {
        String tooLongDescription = "t".repeat(201);
        mockMvc.perform(post("/api/notifications")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "title": "title",
                            "description": "%s"
                        }""", tooLongDescription)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.description")
                        .value(hasItem("Notification description can have at most 200 characters")));
    }

    @Test
    public void createNotificationTooLongTitleAndDescription() throws Exception {
        String tooLongDescription = "t".repeat(201);
        String tooLongTitle = "t".repeat(51);
        mockMvc.perform(post("/api/notifications")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "title": "%s",
                            "description": "%s"
                        }""", tooLongTitle, tooLongDescription)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.title").value(hasItem("Notification title can have at most 50 characters")))
                .andExpect(jsonPath("$.errors.description").value(hasItem("Notification description can have at most 200 characters")));
    }

    @Test
    public void createNotificationSuccess() throws Exception {
        mockMvc.perform(post("/api/notifications")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                        {
                            "title": "Title",
                            "description": "Description"
                        }""")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").hasJsonPath())
                .andExpect(jsonPath("$.data.title").hasJsonPath())
                .andExpect(jsonPath("$.data.description").hasJsonPath())
                .andExpect(jsonPath("$.data.created_at").hasJsonPath())
                .andExpect(jsonPath("$.data.updated_at").hasJsonPath())
                .andExpect(jsonPath("$.data.read").hasJsonPath())
                .andExpect(jsonPath("$.data.user_id", is(ResourceOwnerInjector.id.toString())))
                .andExpect(jsonPath("$.data.title", is("Title")))
                .andExpect(jsonPath("$.data.description", is("Description")));
    }

    @Test
    public void deleteNotificationSuccess() throws Exception {
        Notification notification = createNotificationInDb();
        mockMvc.perform(delete(String.format("/api/notifications/%s", notification.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", is("Notification successfully deleted")));
    }

    @Test
    public void deleteNotificationBadId() throws Exception {
        mockMvc.perform(delete(String.format("/api/notifications/%s", UUID.randomUUID().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnprocessableEntityException))
                .andExpect(jsonPath("$.errors.message").value(hasItem("Request body can not be processed. This notification doesn't exist")));
    }

    private Notification createNotificationInDb() {
        Notification notification = new Notification();
        notification.setTitle("Title");
        notification.setDescription("Description");
        notification.setUserId(ResourceOwnerInjector.id);
        notification.setRead(false);
        notificationRepository.save(notification);
        return notification;
    }




}
