package ba.unsa.etf.nwt.notificationservice.controller;

import ba.unsa.etf.nwt.notificationservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.notificationservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenGenerator tokenGenerator;
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
    /*
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
                .content("""
                        {
                            "title": "Title",
                            "description": "Description"
                        }"""))
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
        Notification notification = createNotificationInDb("Title", "Description", true);
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

    @Test
    public void getNotifications1() throws Exception {
        for(int i = 0; i < 10; i++)
            createNotificationInDb("title", "description", false);

        mockMvc.perform(get("/api/notifications?page=0&size=5")
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
    public void getNotifications2() throws Exception {
        for(int i = 0; i < 10; i++)
            createNotificationInDb("title", "description", false);

        mockMvc.perform(get("/api/notifications?page=1&size=5")
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
    public void getNotifications3() throws Exception {
        for(int i = 0; i < 10; i++)
            createNotificationInDb("title", "description", false);

        mockMvc.perform(get("/api/notifications")
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata").hasJsonPath())
                .andExpect(jsonPath("$.metadata.page_number", is(0)))
                .andExpect(jsonPath("$.metadata.total_elements", is(10)))
                .andExpect(jsonPath("$.metadata.page_size", is(10)))
                .andExpect(jsonPath("$.metadata.has_next", is(false)))
                .andExpect(jsonPath("$.metadata.has_previous", is(false)))
                .andExpect(jsonPath("$.data", hasSize(10)));
    }

    @Test
    public void getNotifications4() throws Exception {
        for(int i = 0; i < 15; i++)
            createNotificationInDb("title", "description", false);

        mockMvc.perform(get("/api/notifications?page=1&size=10")
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata").hasJsonPath())
                .andExpect(jsonPath("$.metadata.page_number", is(1)))
                .andExpect(jsonPath("$.metadata.total_elements", is(15)))
                .andExpect(jsonPath("$.metadata.page_size", is(5)))
                .andExpect(jsonPath("$.metadata.has_next", is(false)))
                .andExpect(jsonPath("$.metadata.has_previous", is(true)))
                .andExpect(jsonPath("$.data", hasSize(5)));
    }

    @Test
    public void getNotifications5() throws Exception {
        var result = mockMvc.perform(get("/api/notifications")
                                        .header(HttpHeaders.AUTHORIZATION, token))
                                        .andExpect(status().isOk())
                                        .andExpect(jsonPath("$.metadata").hasJsonPath())
                                        .andExpect(jsonPath("$.metadata.page_number", is(0)))
                                        .andExpect(jsonPath("$.metadata.total_elements", is(0)))
                                        .andExpect(jsonPath("$.metadata.page_size", is(0)))
                                        .andExpect(jsonPath("$.metadata.has_next", is(false)))
                                        .andExpect(jsonPath("$.metadata.has_previous", is(false)))
                                        .andExpect(jsonPath("$.data", hasSize(0)))
                                        .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 0);
    }

    @Test
    public void testPatchNotificationNotFound() throws Exception {
        mockMvc.perform(patch(String.format("/api/notifications/%s", UUID.randomUUID()))
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Notification not found")));
    }

    @Test
    public void testPatchNotificationNoChange() throws Exception {
        Notification notification = createNotificationInDb("First title", "First description", false);
        mockMvc.perform(patch(String.format("/api/notifications/%s", notification.getId()))
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(notification.getId().toString())))
                .andExpect(jsonPath("$.data.title", is(notification.getTitle())))
                .andExpect(jsonPath("$.data.description", is(notification.getDescription())))
                .andExpect(jsonPath("$.data.read", is(notification.getRead())))
                .andExpect(jsonPath("$.data.user_id", is(ResourceOwnerInjector.id.toString())))
                .andExpect(jsonPath("$.data.title", is("First title")))
                .andExpect(jsonPath("$.data.description", is("First description")));
    }

    @Test
    public void testPatchNotificationNoChange2() throws Exception {
        Notification notification = createNotificationInDb("First title", "First description", false);
        mockMvc.perform(patch(String.format("/api/notifications/%s", notification.getId()))
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                         {
                            "read": false
                        }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(notification.getId().toString())))
                .andExpect(jsonPath("$.data.title", is(notification.getTitle())))
                .andExpect(jsonPath("$.data.description", is(notification.getDescription())))
                .andExpect(jsonPath("$.data.read", is(notification.getRead())))
                .andExpect(jsonPath("$.data.user_id", is(ResourceOwnerInjector.id.toString())))
                .andExpect(jsonPath("$.data.title", is("First title")))
                .andExpect(jsonPath("$.data.description", is("First description")));
    }

    @Test
    public void testPatchNotificationChange1() throws Exception {
        Notification notification = createNotificationInDb("First title", "First description", false);
        mockMvc.perform(patch(String.format("/api/notifications/%s", notification.getId()))
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                         {
                            "read": true
                        }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(notification.getId().toString())))
                .andExpect(jsonPath("$.data.title", is(notification.getTitle())))
                .andExpect(jsonPath("$.data.description", is(notification.getDescription())))
                .andExpect(jsonPath("$.data.read", is(true)))
                .andExpect(jsonPath("$.data.user_id", is(ResourceOwnerInjector.id.toString())))
                .andExpect(jsonPath("$.data.title", is("First title")))
                .andExpect(jsonPath("$.data.description", is("First description")));
    }

    @Test
    public void testPatchNotificationChange2() throws Exception {
        Notification notification = createNotificationInDb("First title", "First description", true);
        mockMvc.perform(patch(String.format("/api/notifications/%s", notification.getId()))
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                         {
                            "read": false
                        }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(notification.getId().toString())))
                .andExpect(jsonPath("$.data.title", is(notification.getTitle())))
                .andExpect(jsonPath("$.data.description", is(notification.getDescription())))
                .andExpect(jsonPath("$.data.read", is(false)))
                .andExpect(jsonPath("$.data.user_id", is(ResourceOwnerInjector.id.toString())))
                .andExpect(jsonPath("$.data.title", is("First title")))
                .andExpect(jsonPath("$.data.description", is("First description")));
    }

    @Test
    public void testPatchNotificationNull() throws Exception {
        Notification notification = createNotificationInDb("title", "description", true);
        mockMvc.perform(patch(String.format("/api/notifications/%s", notification.getId()))
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                         {
                            "read": null
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.read", hasItem("Notification Read attribute can't be null")));
    }

    private Notification createNotificationInDb(String title, String description, boolean read) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setDescription(description);
        notification.setUserId(ResourceOwnerInjector.id);
        notification.setRead(read);
        notificationRepository.save(notification);
        return notification;
    }
    */
}
