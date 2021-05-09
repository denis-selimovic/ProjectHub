package ba.unsa.etf.nwt.emailservice.emailservice.messaging.publishers;

import ba.unsa.etf.nwt.emailservice.emailservice.config.RabbitMQConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.dto.UserDTO;
import ba.unsa.etf.nwt.emailservice.emailservice.messaging.Publisher;
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
