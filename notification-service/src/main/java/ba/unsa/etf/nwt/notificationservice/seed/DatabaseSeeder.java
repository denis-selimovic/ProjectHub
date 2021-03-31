package ba.unsa.etf.nwt.notificationservice.seed;

import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.model.NotificationUser;
import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationRepository;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationUserRepository;
import ba.unsa.etf.nwt.notificationservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final NotificationRepository notificationRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final NotificationUserRepository notificationUserRepository;

    @EventListener
    public void seed(final ContextRefreshedEvent event) {
        if (notificationRepository.count() == 0) {
            seedNotificationsTable();
            seedSubscriptionsTable();
        } else {
            System.out.println("Seeding is not required");
        }
    }

    private void seedNotificationsTable() {
        Notification notification1 = createNotification("New project", "Added you to the team A", false);
        Notification notification2 = createNotification("New task", "Added task UNIT TESTS to the project ProjectHub", true);
        Notification notification3 = createNotification("New bug", "Added bug WRONG EMAIL to the project ProjectHub", false);
        Notification notification4 = createNotification("New comment", "Commented on the task UNIT TESTS", false);
        Notification notification5 = createNotification("New project", "Added you to the team Aplha", true);

        seedNotificationUserTable(List.of(notification1, notification2, notification3, notification4, notification5));
    }

    private void seedNotificationUserTable(List<Notification> notifications) {
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();

        createNotificationUser(notifications.get(0).getId(), user1);
        createNotificationUser(notifications.get(1).getId(), user2);
        createNotificationUser(notifications.get(2).getId(), user1);
        createNotificationUser(notifications.get(3).getId(), user2);
        createNotificationUser(notifications.get(4).getId(), user1);
    }

    private void seedSubscriptionsTable() {
        createSubscription(UUID.randomUUID(), UUID.randomUUID());
        createSubscription(UUID.randomUUID(), UUID.randomUUID());
        createSubscription(UUID.randomUUID(), UUID.randomUUID());
    }

    private Notification createNotification(final String title,
                                            final String description,
                                            boolean read) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setDescription(description);
        notification.setRead(read);

        return notificationRepository.save(notification);
    }

    private void createNotificationUser(UUID notificationId, UUID userId) {
        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setNotificationId(notificationId);
        notificationUser.setUserId(userId);

        notificationUserRepository.save(notificationUser);
    }

    private void createSubscription(UUID userId, UUID taskID) {
        Subscription subscription = new Subscription();
        subscription.setUserId(userId);
        subscription.setTaskId(taskID);

        subscriptionRepository.save(subscription);
    }
}
