package ba.unsa.etf.nwt.projectservice.projectservice.messaging.publishers;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectNotificationDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.messaging.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectNotificationPublisher implements Publisher<ProjectNotificationDTO> {

    private final RabbitTemplate template;
    private final DirectExchange directExchange;

    @Value("${amqp.routing-key}")
    private String routingKey;

    @Override
    public void send(ProjectNotificationDTO data) {
        this.template.convertAndSend(directExchange.getName(), routingKey, data);
    }
}
