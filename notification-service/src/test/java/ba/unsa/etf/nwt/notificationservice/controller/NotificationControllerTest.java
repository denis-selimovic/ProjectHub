package ba.unsa.etf.nwt.notificationservice.controller;

import ba.unsa.etf.nwt.notificationservice.repository.NotificationRepository;
import ba.unsa.etf.nwt.notificationservice.service.NotificationService;
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
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    private String token;

    @BeforeEach
    public void setUp() {
        notificationRepository.deleteAll();
    }
}
