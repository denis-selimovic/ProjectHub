package ba.unsa.etf.nwt.notificationservice.seed;

import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.model.NotificationUser;
import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import ba.unsa.etf.nwt.notificationservice.model.SubscriptionConfig;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationRepository;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationUserRepository;
import ba.unsa.etf.nwt.notificationservice.repository.SubscriptionConfigRepository;
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
    private final SubscriptionConfigRepository subscriptionConfigRepository;

    @EventListener
    public void seed(final ContextRefreshedEvent event) {
        if (notificationRepository.count() == 0) {
            seedNotificationsTable();
            seedSubscriptionConfigTable();
        } else {
            System.out.println("Seeding is not required");
        }
    }

    private void seedNotificationsTable() {
        Notification notification1 = createNotification("New project", "Added you to the team A");
        Notification notification2 = createNotification("New task", "Added task UNIT TESTS to the project ProjectHub");
        Notification notification3 = createNotification("New bug", "Added bug WRONG EMAIL to the project ProjectHub");
        Notification notification4 = createNotification("New comment", "Commented on the task UNIT TESTS");
        Notification notification5 = createNotification("New project", "Added you to the team Aplha");

        seedNotificationUserTable(List.of(notification1, notification2, notification3, notification4, notification5));
    }

    private void seedNotificationUserTable(List<Notification> notifications) {
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();

        createNotificationUser(notifications.get(0), user1, false);
        createNotificationUser(notifications.get(1), user2, false);
        createNotificationUser(notifications.get(2), user1, true);
        createNotificationUser(notifications.get(3), user2, true);
        createNotificationUser(notifications.get(4), user1, true);
    }

    private void seedSubscriptionsTable(List<SubscriptionConfig> configs) {
        for (var config : configs) {
            createSubscription(UUID.randomUUID(), config);
        }
    }

    private void seedSubscriptionConfigTable() {
        var config1 = createSubscriptionConfig(UUID.randomUUID(), "lamija.vrnjak@gmail.com");
        var config2 = createSubscriptionConfig(UUID.randomUUID(), "amila.zigo@gmail.com");
        var config3 = createSubscriptionConfig(UUID.randomUUID(), "denis.selimovic@gmail.com");

        seedSubscriptionsTable(List.of(config1, config2, config3));
    }

    private Notification createNotification(final String title, final String description) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setDescription(description);

        return notificationRepository.save(notification);
    }

    private void createNotificationUser(Notification notification, UUID userId, boolean read) {
        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setNotification(notification);
        notificationUser.setUserId(userId);
        notificationUser.setRead(read);

        notificationUserRepository.save(notificationUser);
    }

    private void createSubscription(UUID taskId, SubscriptionConfig config) {
        Subscription subscription = new Subscription();
        subscription.setConfig(config);
        subscription.setTaskId(taskId);

        subscriptionRepository.save(subscription);
    }

    private SubscriptionConfig createSubscriptionConfig(UUID userId, String email) {
        SubscriptionConfig subscriptionConfig = new SubscriptionConfig();
        subscriptionConfig.setUserId(userId);
        subscriptionConfig.setEmail(email);
        return subscriptionConfigRepository.save(subscriptionConfig);
    }
}
