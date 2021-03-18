package ba.unsa.etf.nwt.userservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TokenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWrongGrantType() throws Exception {
        mockMvc.perform(post("/oauth/token")
                .content("{\"grant_type\":\"passwordd\"}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.grant_type", is("OAuth2 grant not supported")));
    }
}
