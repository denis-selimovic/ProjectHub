package ba.unsa.etf.nwt.notificationservice.messaging.publishers;

import ba.unsa.etf.nwt.notificationservice.config.RabbitMQConfig;
import ba.unsa.etf.nwt.notificationservice.dto.UserDTO;
import ba.unsa.etf.nwt.notificationservice.messaging.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPublisher implements Publisher<UserDTO> {

    private final RabbitTemplate template;

    @Override
    public void send(UserDTO data) {
        this.template.convertAndSend(
                RabbitMQConfig.DeleteUserQueueConfig.EXCHANGE_NAME,
                RabbitMQConfig.DeleteUserQueueConfig.ROUTING_KEY,
                data);
    }
}
