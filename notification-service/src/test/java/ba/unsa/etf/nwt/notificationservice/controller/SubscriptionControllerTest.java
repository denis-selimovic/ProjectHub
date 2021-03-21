package ba.unsa.etf.nwt.notificationservice.controller;


import ba.unsa.etf.nwt.notificationservice.repository.SubscriptionRepository;
import ba.unsa.etf.nwt.notificationservice.service.SubscriptionService;
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
    private SubscriptionService subscriptionService;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private String token;

    @BeforeEach
    public void setUp() {
        subscriptionRepository.deleteAll();
    }
}
