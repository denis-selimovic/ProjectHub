package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
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
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    public void setUp() {
        projectRepository.deleteAll();
    }

    @Test
    public void testBlankName() throws Exception {
        String error = "Project name can't be blank";
        mockMvc.perform(post("/api/projects/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": ""
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error.name").value(hasItem(error)));
    }

    @Test
    public void testLongName() throws Exception {
        String error = "Project name can contain at most 50 characters";
        mockMvc.perform(post("/api/projects/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "projekatprojekatprojekatprojekatprojekatprojekatprojekatprojekat"
                        }"""))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error.name").value(hasItem(error)));
    }

    @Test
    public void testProjectExists() throws Exception {
        mockMvc.perform(post("/api/projects/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Project name"
                        }"""
                )
        );

        mockMvc.perform(post("/api/projects/add")
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
        mockMvc.perform(post("/api/projects/add")
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


}
