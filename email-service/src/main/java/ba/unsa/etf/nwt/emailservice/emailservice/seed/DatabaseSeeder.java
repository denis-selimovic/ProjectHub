package ba.unsa.etf.nwt.emailservice.emailservice.seed;

import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailSubscription;
import ba.unsa.etf.nwt.emailservice.emailservice.repository.EmailConfigRepository;
import ba.unsa.etf.nwt.emailservice.emailservice.repository.EmailSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final EmailConfigRepository emailConfigRepository;
    private final EmailSubscriptionRepository emailSubscriptionRepository;

    @EventListener
    public void seed(final ContextRefreshedEvent event) {
        seedEmailConfigsTable();
    }

    private void seedEmailConfigsTable() {
        if (emailConfigRepository.count() == 0) {
            EmailConfig e1 = createEmailConfig("admin@projecthub.com", UUID.randomUUID());
            EmailConfig e2 = createEmailConfig("info@projecthub.com", UUID.randomUUID());
            EmailConfig e3 = createEmailConfig("support@projecthub.com", UUID.randomUUID());

            emailConfigRepository.save(e1);
            emailConfigRepository.save(e2);
            emailConfigRepository.save(e3);
            
            seedEmailSubscriptionsTable(List.of(e1, e2, e3));

        } else {
            System.out.println("Seeding is not required");
        }
    }

    private void seedEmailSubscriptionsTable(List<EmailConfig> configs) {
        emailSubscriptionRepository.save(createEmailSubscription(configs.get(0), UUID.randomUUID()));
        emailSubscriptionRepository.save(createEmailSubscription(configs.get(1), UUID.randomUUID()));
        emailSubscriptionRepository.save(createEmailSubscription(configs.get(2), UUID.randomUUID()));
    }

    private EmailConfig createEmailConfig(final String email, final UUID userId) {
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setEmail(email);
        emailConfig.setUserId(userId);
        return emailConfig;
    }

    private EmailSubscription createEmailSubscription(final EmailConfig emailConfig, final UUID taskId) {
        EmailSubscription emailSubscription = new EmailSubscription();
        emailSubscription.setConfig(emailConfig);
        emailSubscription.setTask(taskId);
        return emailSubscription;
    }
}
