package ba.unsa.etf.nwt.userservice.controller;

import ba.unsa.etf.nwt.userservice.config.OAuth2ClientDetails;
import ba.unsa.etf.nwt.userservice.config.OAuth2ClientDetailsRepository;
import ba.unsa.etf.nwt.userservice.model.User;
import ba.unsa.etf.nwt.userservice.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TokenControllerTest {

    private final static String CLIENT_ID = "test";
    private final static String CLIENT_SECRET = "test";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OAuth2ClientDetailsRepository oAuth2ClientDetailsRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        oAuth2ClientDetailsRepository.deleteAll();
        oAuth2ClientDetailsRepository.save(new OAuth2ClientDetails(
                CLIENT_ID,
                "oauth2-resource",
                passwordEncoder.encode(CLIENT_SECRET),
                "read,write",
                "password,refresh_token",
                null,
                null,
                600,
                900,
                null,
                null
        ));
    }

    @Test
    public void testSuccessfulLogin() throws Exception {
        User user = createUserInDb("email@email.com", "test", "Test", "Test", true);
        mockMvc.perform(post("/oauth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth(CLIENT_ID, CLIENT_SECRET, null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPasswordGrantType(user.getEmail(), "test"))
        ).andExpect(status().isOk());
    }

    @Test
    public void testWrongClientSecet() throws Exception {
        User user = createUserInDb("email@email.com", "test", "Test", "Test", true);
        mockMvc.perform(post("/oauth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth(CLIENT_ID, "", null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPasswordGrantType(user.getEmail(), "test"))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void testWrongClientId() throws Exception {
        User user = createUserInDb("email@email.com", "test", "Test", "Test", true);
        mockMvc.perform(post("/oauth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth("", CLIENT_SECRET, null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPasswordGrantType(user.getEmail(), "test"))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void testMissingGrantType() throws Exception {
        User user = createUserInDb("email@email.com", "test", "Test", "Test", true);
        mockMvc.perform(post("/oauth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth(CLIENT_ID, CLIENT_SECRET, null))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "ok",
                            "password": "ok"
                        }
                        """)
        ).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testBadPassword() throws Exception {
        User user = createUserInDb("email@email.com", "test", "Test", "Test", true);
        mockMvc.perform(post("/oauth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth(CLIENT_ID, CLIENT_SECRET, null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPasswordGrantType(user.getEmail(), "wrong_pw"))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testWrongUsername() throws Exception {
        User user = createUserInDb("email@email.com", "test", "Test", "Test", true);
        mockMvc.perform(post("/oauth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth(CLIENT_ID, CLIENT_SECRET, null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPasswordGrantType("nope", "test"))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void testRefreshToken() throws Exception {
        User user = createUserInDb("email@email.com", "test", "Test", "Test", true);
        var response = mockMvc.perform(post("/oauth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth(CLIENT_ID, CLIENT_SECRET, null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPasswordGrantType(user.getEmail(), "test"))
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JSONObject data = new JSONObject(response).getJSONObject("data");
        mockMvc.perform(post("/oauth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth(CLIENT_ID, CLIENT_SECRET, null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRefreshTokenGrantType(data.getString("refresh_token")))
        ).andExpect(status().isOk());
    }

    @Test
    public void wrongRefreshToken() throws Exception {
        mockMvc.perform(post("/oauth/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + HttpHeaders.encodeBasicAuth(CLIENT_ID, CLIENT_SECRET, null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRefreshTokenGrantType("refresh_token"))
        ).andExpect(status().isBadRequest());
    }

    private User createUserInDb(String email, String password, String firstName, String lastName, Boolean enabled) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(enabled);
        return userRepository.save(user);
    }

    private String createPasswordGrantType(String email, String password) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("email", email);
        object.put("password", password);
        object.put("grant_type", "password");
        return object.toString();
    }

    private String createRefreshTokenGrantType(String refresh_token) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("grant_type", "refresh_token");
        object.put("refresh_token", refresh_token);
        return object.toString();
    }
}
