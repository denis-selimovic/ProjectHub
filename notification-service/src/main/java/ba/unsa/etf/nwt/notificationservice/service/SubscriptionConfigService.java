package ba.unsa.etf.nwt.notificationservice.service;

import ba.unsa.etf.nwt.notificationservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.model.SubscriptionConfig;
import ba.unsa.etf.nwt.notificationservice.repository.SubscriptionConfigRepository;
import ba.unsa.etf.nwt.notificationservice.request.SubscriptionConfigRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionConfigService {
    private final SubscriptionConfigRepository subscriptionConfigRepository;

    public SubscriptionConfig create(final SubscriptionConfigRequest request) {
        subscriptionConfigRepository.findByEmailOrUserId(request.getEmail(), request.getUserId()).ifPresent(c -> {
            throw new UnprocessableEntityException("Config with this email or user id already exists");
        });

        SubscriptionConfig config = createConfigFromRequest(request);
        return subscriptionConfigRepository.save(config);
    }

    private SubscriptionConfig createConfigFromRequest(SubscriptionConfigRequest request) {
        SubscriptionConfig subscriptionConfig = new SubscriptionConfig();
        subscriptionConfig.setUserId(request.getUserId());
        subscriptionConfig.setEmail(request.getEmail());

        return subscriptionConfig;
    }

    public SubscriptionConfig findByUserId(final UUID userId) {
        return subscriptionConfigRepository
                .findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Config not found"));
    }
}
