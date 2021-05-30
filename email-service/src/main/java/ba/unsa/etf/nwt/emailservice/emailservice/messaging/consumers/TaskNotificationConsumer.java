package ba.unsa.etf.nwt.emailservice.emailservice.messaging.consumers;

import ba.unsa.etf.nwt.emailservice.emailservice.dto.TaskNotificationDTO;
import ba.unsa.etf.nwt.emailservice.emailservice.messaging.Consumer;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.service.EmailConfigService;
import ba.unsa.etf.nwt.emailservice.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskNotificationConsumer implements Consumer<TaskNotificationDTO> {

    private final EmailConfigService emailConfigService;
    private final EmailService emailService;

    @RabbitListener(queues = "task-notification-email-queue")
    public void receive(TaskNotificationDTO data) {
        UUID id = UUID.fromString(data.getChanges().get("userId").current);
        EmailConfig config = emailConfigService.findByUserId(id);
        String email = config.getEmail();
        emailService.sendNotificationEmail(email, data.getTaskName());
    }
}
