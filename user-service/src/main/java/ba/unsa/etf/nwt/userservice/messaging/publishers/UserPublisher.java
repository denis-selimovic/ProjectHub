package ba.unsa.etf.nwt.userservice.messaging.publishers;

import ba.unsa.etf.nwt.userservice.config.RabbitMQConfig;
import ba.unsa.etf.nwt.userservice.dto.UserDTO;
import ba.unsa.etf.nwt.userservice.messaging.Publisher;
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
                RabbitMQConfig.CreateUserQueueConfig.EXCHANGE_NAME,
                RabbitMQConfig.CreateUserQueueConfig.ROUTING_KEY,
                data
        );
    }
}
