package ba.unsa.etf.nwt.notificationservice.service;

import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import ba.unsa.etf.nwt.notificationservice.repository.SubscriptionRepository;
import ba.unsa.etf.nwt.notificationservice.request.CreateSubscriptionRequest;
import ba.unsa.etf.nwt.notificationservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public Subscription create(@RequestBody @Valid CreateSubscriptionRequest request, ResourceOwner resourceOwner) {

        subscriptionRepository.findByTaskIdAndUserId(request.getTaskId(), resourceOwner.getId()).ifPresent(p -> {
            throw new UnprocessableEntityException("Request body can not be processed. This user is already subscribed to the task.");
        });

        Subscription subscription = new Subscription();
        subscription.setUserId(resourceOwner.getId());
        subscription.setTaskId(request.getTaskId());
        subscriptionRepository.save(subscription);
        return subscription;
    }

    public boolean existsById(UUID subscriptionId) {
        return subscriptionRepository.existsById(subscriptionId);
    }

    public void deleteById(UUID subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }
}
