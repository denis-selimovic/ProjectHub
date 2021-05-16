package ba.unsa.etf.nwt.notificationservice.messaging.consumers;

import ba.unsa.etf.nwt.notificationservice.dto.TaskNotificationDTO;
import ba.unsa.etf.nwt.notificationservice.messaging.Consumer;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import ba.unsa.etf.nwt.notificationservice.service.NotificationService;
import ba.unsa.etf.nwt.notificationservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskNotificationConsumer implements Consumer<TaskNotificationDTO> {

    private final SubscriptionService subscriptionService;
    private final NotificationService notificationService;

    @RabbitListener(queues = "task-notification-queue")
    public void receive(TaskNotificationDTO data) {
        updateSubscriptions(data);
        createNotifications(data);
        System.out.println(data);
    }

    private void updateSubscriptions(TaskNotificationDTO data) {
        TaskNotificationDTO.Change change = data.getChanges().get("userId");
        if (change == null) return;

        if (change.previous != null) {
            UUID id = UUID.fromString(change.previous);
            subscriptionService.deleteByTask(data.getTaskId(), id);
        }
        if (change.current != null) {
            UUID id = UUID.fromString(change.current);
            subscriptionService.create(data.getTaskId(), id);
        }
    }

    private void createNotifications(TaskNotificationDTO data) {
        for (var entry: data.getChanges().entrySet()) {
            if (entry.getKey().equals("userId")) continue;
            Notification notification = createNotification(data.getTaskName(), entry);
            createNotificationUsers(data.getTaskId(), UUID.fromString(data.getUpdatedBy()), notification);
        }
    }

    private void createNotificationUsers(UUID taskId, UUID updatedAt, Notification notification) {
        Set<Subscription> subscriptions = subscriptionService.findTaskSubscriptions(taskId);
        for(var subscription: subscriptions) {
            if (subscription.getConfig().getUserId().equals(updatedAt)) continue;
            notificationService.createNotificationUser(subscription.getConfig().getUserId(), notification);
        }
    }

    private Notification createNotification(String taskName, Map.Entry<String, TaskNotificationDTO.Change> entry) {
        String key = entry.getKey();
        TaskNotificationDTO.Change change = entry.getValue();
        String title = String.format("Task %s updated", taskName);
        String description = String.format("Task %s updated from %s to %s.", key, change.previous, change.current);
        return notificationService.create(title, description);
    }
}
