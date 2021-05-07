package ba.unsa.etf.nwt.emailservice.emailservice.messaging.publishers;

import ba.unsa.etf.nwt.emailservice.emailservice.dto.UserDTO;
import ba.unsa.etf.nwt.emailservice.emailservice.messaging.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPublisher implements Publisher<UserDTO> {
    private final RabbitTemplate template;
    private final DirectExchange exchange;

    @Value("${amqp.routing-key}")
    private String routingKey;

    @Override
    public void send(UserDTO data) {
        this.template.convertAndSend(exchange.getName(), routingKey, data);
    }
}
