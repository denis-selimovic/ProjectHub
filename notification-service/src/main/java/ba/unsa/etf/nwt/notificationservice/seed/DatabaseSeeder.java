package ba.unsa.etf.nwt.notificationservice.seed;

import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationRepository;
import ba.unsa.etf.nwt.notificationservice.repository.NotificationSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final NotificationRepository notificationRepository;
    private final NotificationSubscriptionRepository subscriptionRepository;

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
            createNotification(
                    "New project",
                    "Added you to the team A",
                    Instant.now(),
                    null,
                    false,
                    UUID.randomUUID());

            createNotification(
                    "New task",
                    "Added task UNIT TESTS to the project ProjectHub",
                    Instant.now(),
                    null,
                    true,
                    UUID.randomUUID());

            createNotification(
                    "New bug",
                    "Added bug WRONG EMAIL to the project ProjectHub",
                    Instant.now(),
                    null,
                    false,
                    UUID.randomUUID());

            createNotification(
                    "New comment",
                    "Commented on the task UNIT TESTS",
                    Instant.now(),
                    null,
                    false,
                    UUID.randomUUID());

            createNotification(
                    "New project",
                    "Added you to the team Aplha",
                    Instant.now(),
                    null,
                    true,
                    UUID.randomUUID());
    }

    private void createNotification(final String title,
                                            final String description,
                                            Instant createdAt,
                                            Instant updatedAt,
                                            boolean read,
                                            UUID userId) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setDescription(description);
        notification.setCreatedAt(createdAt);
        notification.setUpdatedAt(updatedAt);
        notification.setRead(read);
        notification.setUserId(userId);

        notificationRepository.save(notification);
    }

    private void seedSubscriptionsTable() {
        createSubscription(Instant.now(), null, UUID.randomUUID(), UUID.randomUUID());
        createSubscription(Instant.parse("2020-04-09T10:15:30.00Z"), Instant.now(), UUID.randomUUID(), UUID.randomUUID());
        createSubscription(Instant.now(), null, UUID.randomUUID(), UUID.randomUUID());
    }

    private void createSubscription(Instant createdAt, Instant updatedAt, UUID userId, UUID taskID) {
        Subscription subscription = new Subscription();
        subscription.setCreatedAt(createdAt);
        subscription.setUpdatedAt(updatedAt);
        subscription.setUserId(userId);
        subscription.setTaskId(taskID);

        subscriptionRepository.save(subscription);
    }
}
