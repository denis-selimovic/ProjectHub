package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.config.TokenGenerator;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.*;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TokenGenerator tokenGenerator;

    @BeforeEach
    public void setUp() {
        projectRepository.deleteAll();
    }

    @Test
    public void testBlankName() throws Exception {
        mockMvc.perform(post("/api/v1/projects/add")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": ""
                        }"""))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testLongName() throws Exception {
        String error = "Project name can contain at most 50 characters";
        mockMvc.perform(post("/api/v1/projects/add")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client0",
                        UUID.randomUUID(),
                        "email0@email.com"
                ).getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "projekatprojekatprojekatprojekatprojekatprojekatprojekatprojekat"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.name").value(hasItem(error)));
    }

    @Test
    public void testProjectExists() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/projects/add")
                                                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                                                        "client1",
                                                        UUID.randomUUID(),
                                                        "email1@email.com"
                                                ).getValue());
        mockMvc.perform(request
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Project name"
                        }"""
                )
        );

        mockMvc.perform(request
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Project name"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.message", hasItem("Request body can not be processed")));
    }

    @Test
    public void testAddProjectSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/projects/add")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client2",
                        UUID.randomUUID(),
                        "email2@email.com"
                ).getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Project name"
                        }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").hasJsonPath())
                .andExpect(jsonPath("$.data.nam").hasJsonPath())
                .andExpect(jsonPath("$.data.owner_id").hasJsonPath())
                .andExpect(jsonPath("$.data.created_at").hasJsonPath())
                .andExpect(jsonPath("$.data.updated_at").hasJsonPath());
    }

    @Test
    public void testDeleteNonexistentProject() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/" + UUID.randomUUID())
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue()))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDeleteForeignProject() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/v1/projects/add")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue());

        MvcResult result = mockMvc.perform(request
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Project name"
                        }"""))
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        JSONObject resultContentJson = new JSONObject(resultContent);
        UUID projectId = UUID.fromString(resultContentJson.getJSONObject("data").getString("id"));

        mockMvc.perform(delete("/api/projects/" + projectId)
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client2",
                        UUID.randomUUID(),
                        "email2@email.com"
                ).getValue()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testSuccessfulDeletion() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/v1/projects/add")
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client",
                        UUID.randomUUID(),
                        "email@email.com"
                ).getValue());

        MvcResult result = mockMvc.perform(request
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Project name"
                        }"""))
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        JSONObject resultContentJson = new JSONObject(resultContent);
        UUID projectId = UUID.fromString(resultContentJson.getJSONObject("data").getString("id"));

        mockMvc.perform(delete("/api/projects/" + projectId)
                .header("Authorization", "Bearer " + tokenGenerator.createAccessToken(
                        "client2",
                        UUID.randomUUID(),
                        "email2@email.com"
                ).getValue()))
                .andExpect(status().isOk());
    }
}
