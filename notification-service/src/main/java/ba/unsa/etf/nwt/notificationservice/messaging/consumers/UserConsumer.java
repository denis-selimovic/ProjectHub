package ba.unsa.etf.nwt.notificationservice.messaging.consumers;

import ba.unsa.etf.nwt.notificationservice.dto.UserDTO;
import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.messaging.Consumer;
import ba.unsa.etf.nwt.notificationservice.messaging.publishers.UserPublisher;
import ba.unsa.etf.nwt.notificationservice.service.SubscriptionConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConsumer implements Consumer<UserDTO> {

    private final SubscriptionConfigService subscriptionConfigService;
    private final UserPublisher userPublisher;

    @RabbitListener(queues = "create-user-notif-queue")
    public void receive(UserDTO data) {
        try {
            System.out.println(data);
            subscriptionConfigService.create(data);
        } catch (UnprocessableEntityException e) {
            System.out.println("NOT OK");
            userPublisher.send(data);
        }
    }
}
