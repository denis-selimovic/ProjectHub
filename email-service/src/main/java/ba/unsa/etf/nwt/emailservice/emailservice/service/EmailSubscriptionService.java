package ba.unsa.etf.nwt.emailservice.emailservice.service;

import ba.unsa.etf.nwt.emailservice.emailservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailSubscription;
import ba.unsa.etf.nwt.emailservice.emailservice.repository.EmailSubscriptionRepository;
import ba.unsa.etf.nwt.emailservice.emailservice.request.CreateSubscriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSubscriptionService {
    private final EmailSubscriptionRepository emailSubscriptionRepository;

    public EmailSubscription create(CreateSubscriptionRequest request, EmailConfig emailConfig) {
        emailSubscriptionRepository.findByTask(request.getTaskId()).ifPresent(t -> {
            throw new UnprocessableEntityException("Request cannot be processed");
        });
        EmailSubscription subscription = request.createSubscription();
        subscription.setConfig(emailConfig);
        return emailSubscriptionRepository.save(subscription);
    }
}
