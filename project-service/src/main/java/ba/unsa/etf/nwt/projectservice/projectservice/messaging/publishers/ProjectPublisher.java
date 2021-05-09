package ba.unsa.etf.nwt.projectservice.projectservice.messaging.publishers;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectNotificationDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.messaging.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectPublisher implements Publisher<ProjectDTO> {

    private final RabbitTemplate template;
    private final DirectExchange exchange;

    @Value("${amqp.routing-key}")
    private String routingKey;

    @Override
    public void send(ProjectDTO data) {
        this.template.convertAndSend(exchange.getName(), routingKey, data);
    }
}
