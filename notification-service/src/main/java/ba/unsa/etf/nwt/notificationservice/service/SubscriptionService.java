package ba.unsa.etf.nwt.notificationservice.service;

import ba.unsa.etf.nwt.notificationservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import ba.unsa.etf.nwt.notificationservice.model.SubscriptionConfig;
import ba.unsa.etf.nwt.notificationservice.repository.SubscriptionRepository;
import ba.unsa.etf.nwt.notificationservice.request.CreateSubscriptionRequest;
import ba.unsa.etf.nwt.notificationservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionConfigService subscriptionConfigService;

    public Subscription create(@RequestBody @Valid CreateSubscriptionRequest request, ResourceOwner resourceOwner) {
        return create(request.getTaskId(), resourceOwner.getId());
    }

    public void deleteBySubscription(UUID subscriptionId, UUID userId) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findByIdAndConfig_UserId(subscriptionId, userId);
        if(optionalSubscription.isEmpty()){
            throw new NotFoundException("Subscription doesn't exist");
        }
        subscriptionRepository.deleteById(subscriptionId);
    }

    public Subscription create(UUID taskId, UUID userId) {
        SubscriptionConfig config = subscriptionConfigService.findByUserId(userId);
        subscriptionRepository.findByTaskIdAndConfig(taskId, config).ifPresent(p -> {
            throw new UnprocessableEntityException("You have already subscribed to the task.");
        });
        Subscription subscription = new Subscription();
        subscription.setConfig(config);
        subscription.setTaskId(taskId);
        return subscriptionRepository.save(subscription);
    }

    public void deleteByTask(UUID taskId, UUID userId) {
        subscriptionRepository.findByTaskIdAndConfig_UserId(taskId, userId).ifPresent(s -> {
            subscriptionRepository.deleteById(s.getId());
        });
    }

    public Set<Subscription> findTaskSubscriptions(UUID taskId) {
        return subscriptionRepository.findAllByTaskId(taskId);
    }
}
