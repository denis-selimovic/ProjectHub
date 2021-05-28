package ba.unsa.etf.nwt.notificationservice.controller;

import ba.unsa.etf.nwt.notificationservice.config.token.ResourceOwnerInjector;
import ba.unsa.etf.nwt.notificationservice.config.token.TokenGenerator;
import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.model.NotificationUser;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationRepository;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationUserRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationUserRepository notificationUserRepository;

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
        mockMvc.perform(post("/api/v1/notifications")
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
        mockMvc.perform(post("/api/v1/notifications")
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
        mockMvc.perform(post("/api/v1/notifications")
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
        mockMvc.perform(post("/api/v1/notifications")
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
        mockMvc.perform(post("/api/v1/notifications")
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
        mockMvc.perform(post("/api/v1/notifications")
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
        mockMvc.perform(post("/api/v1/notifications")
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
                .andExpect(jsonPath("$.data.title", is("Title")))
                .andExpect(jsonPath("$.data.description", is("Description")))
                .andExpect(jsonPath("$.data.read", is(false)));
    }

    @Test
    public void deleteNotificationSuccess() throws Exception {
        Notification notification = createNotificationInDb("Title", "Description", true);
        mockMvc.perform(delete(String.format("/api/v1/notifications/%s", notification.getId()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message", is("Notification successfully deleted")));
    }

    @Test
    public void deleteNotificationBadId() throws Exception {
        mockMvc.perform(delete(String.format("/api/v1/notifications/%s", UUID.randomUUID().toString()))
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnprocessableEntityException))
                .andExpect(jsonPath("$.errors.message").value(hasItem("Notification doesn't exist")));
    }

    @Test
    public void getNotifications1() throws Exception {
        for(int i = 0; i < 10; i++)
            createNotificationInDb("title", "description", false);

        mockMvc.perform(get("/api/v1/notifications?page=0&size=5")
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

        mockMvc.perform(get("/api/v1/notifications?page=1&size=5")
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

        mockMvc.perform(get("/api/v1/notifications")
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

        mockMvc.perform(get("/api/v1/notifications?page=1&size=10")
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
        var result = mockMvc.perform(get("/api/v1/notifications")
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
        mockMvc.perform(patch(String.format("/api/v1/notifications/%s", UUID.randomUUID()))
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.message", hasItem("Notification not found")));
    }

    @Test
    public void testPatchNotificationNoChange() throws Exception {
        Notification notification = createNotificationInDb("First title", "First description", false);
        mockMvc.perform(patch(String.format("/api/v1/notifications/%s", notification.getId()))
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(notification.getId().toString())))
                .andExpect(jsonPath("$.data.title", is(notification.getTitle())))
                .andExpect(jsonPath("$.data.description", is(notification.getDescription())))
                .andExpect(jsonPath("$.data.title", is("First title")))
                .andExpect(jsonPath("$.data.read", is(false)))
                .andExpect(jsonPath("$.data.description", is("First description")));
    }

    @Test
    public void testPatchNotificationNoChange2() throws Exception {
        Notification notification = createNotificationInDb("First title", "First description", false);
        mockMvc.perform(patch(String.format("/api/v1/notifications/%s", notification.getId()))
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
                .andExpect(jsonPath("$.data.title", is("First title")))
                .andExpect(jsonPath("$.data.read", is(false)))
                .andExpect(jsonPath("$.data.description", is("First description")));
    }

    @Test
    public void testPatchNotificationChange1() throws Exception {
        Notification notification = createNotificationInDb("First title", "First description", false);
        mockMvc.perform(patch(String.format("/api/v1/notifications/%s", notification.getId()))
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
                .andExpect(jsonPath("$.data.title", is("First title")))
                .andExpect(jsonPath("$.data.read", is(true)))
                .andExpect(jsonPath("$.data.description", is("First description")));
    }

    @Test
    public void testPatchNotificationChange2() throws Exception {
        Notification notification = createNotificationInDb("First title", "First description", true);
        mockMvc.perform(patch(String.format("/api/v1/notifications/%s", notification.getId()))
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
                .andExpect(jsonPath("$.data.title", is("First title")))
                .andExpect(jsonPath("$.data.read", is(false)))
                .andExpect(jsonPath("$.data.description", is("First description")));
    }

    @Test
    public void testPatchNotificationNull() throws Exception {
        Notification notification = createNotificationInDb("title", "description", true);
        mockMvc.perform(patch(String.format("/api/v1/notifications/%s", notification.getId()))
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                         {
                            "read": null
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.read", hasItem("Notification Read attribute can't be null")));
    }

    @Test
    public void getNotificationsCheckSort() throws Exception {
        for(int i = 0; i < 5; i++)
            createNotificationInDb("title", "description", false);
        for(int i = 0; i < 3; i++)
            createNotificationInDb("title", "description", true);
        for(int i = 0; i < 7; i++)
            createNotificationInDb("title", "description", false);

        var result = mockMvc.perform(get("/api/v1/notifications")
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 15);

        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < data.length(); i++) {
            jsonValues.add(data.getJSONObject(i));
        }

        List<JSONObject> sorted = jsonValues.stream().sorted((n1, n2) -> {
                try {
                    if (n1.get("read").equals(n2.get("read"))) {
                        return -Instant.parse(n1.get("created_at").toString())
                                .compareTo(Instant.parse(n2.get("created_at").toString()));
                    }else {
                        return Boolean.compare(Boolean.parseBoolean(n1.get("read").toString()),
                                Boolean.parseBoolean(n2.get("read").toString()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return 1;
            }).collect(Collectors.toList());
        assertEquals(sorted, jsonValues);
    }

    @Test
    public void getReadNotifications() throws Exception {
        for(int i = 0; i < 5; i++)
            createNotificationInDb("title", "description", false);
        for(int i = 0; i < 3; i++)
            createNotificationInDb("title", "description", true);
        for(int i = 0; i < 7; i++)
            createNotificationInDb("title", "description", false);

        var result = mockMvc.perform(get("/api/v1/notifications?read=true")
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 3);

        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < data.length(); i++) {
            jsonValues.add(data.getJSONObject(i));
            assertTrue((Boolean) data.getJSONObject(i).get("read"));
        }

        List<JSONObject> sorted = jsonValues.stream().sorted((n1, n2) -> {
            try {
                return -Instant.parse(n1.get("created_at").toString())
                        .compareTo(Instant.parse(n2.get("created_at").toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 1;
        }).collect(Collectors.toList());
        assertEquals(sorted, jsonValues);
    }

    @Test
    public void getUnreadNotifications() throws Exception {
        for(int i = 0; i < 5; i++)
            createNotificationInDb("title", "description", false);
        for(int i = 0; i < 3; i++)
            createNotificationInDb("title", "description", true);
        for(int i = 0; i < 7; i++)
            createNotificationInDb("title", "description", false);

        var result = mockMvc.perform(get("/api/v1/notifications?read=false&size=20")
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertEquals(data.length(), 12);

        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < data.length(); i++) {
            jsonValues.add(data.getJSONObject(i));
            assertFalse((Boolean) data.getJSONObject(i).get("read"));
        }

        List<JSONObject> sorted = jsonValues.stream().sorted((n1, n2) -> {
            try {
                return -Instant.parse(n1.get("created_at").toString())
                        .compareTo(Instant.parse(n2.get("created_at").toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 1;
        }).collect(Collectors.toList());
        assertEquals(sorted, jsonValues);
    }

    private Notification createNotificationInDb(String title, String description, boolean read) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setDescription(description);
        notificationRepository.save(notification);
        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setUserId(ResourceOwnerInjector.id);
        notificationUser.setNotification(notification);
        notificationUser.setRead(read);
        notificationUserRepository.save(notificationUser);
        return notification;
    }
}
