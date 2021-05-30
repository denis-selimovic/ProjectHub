package ba.unsa.etf.nwt.notificationservice.messaging.consumers;

import ba.unsa.etf.nwt.notificationservice.dto.ProjectNotificationDTO;
import ba.unsa.etf.nwt.notificationservice.messaging.Consumer;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.service.NotificationService;
import com.google.gson.JsonObject;
import com.pusher.rest.Pusher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectNotificationConsumer implements Consumer<ProjectNotificationDTO> {

    private final NotificationService notificationService;
    private final Pusher pusher;

    @RabbitListener(queues = "project-notification-queue")
    public void receive(ProjectNotificationDTO data) {
        Notification notification = notificationService.create(data.getTitle(), data.getDescription());
        notificationService.createNotificationUser(data.getUserId(), notification);
        pusher.trigger("private-" + data.getUserId().toString(), "notification",
                notification);
    }
}
