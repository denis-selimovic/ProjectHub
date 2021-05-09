package ba.unsa.etf.nwt.taskservice.messaging.publishers;

import ba.unsa.etf.nwt.taskservice.dto.ProjectDTO;
import ba.unsa.etf.nwt.taskservice.messaging.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectPublisher implements Publisher<ProjectDTO> {

    private final RabbitTemplate template;
    private final DirectExchange deletingExchange;
    private final String routingKey = "deleting-routing-key";

    @Override
    public void send(ProjectDTO data) {
        this.template.convertAndSend(deletingExchange.getName(), routingKey, data);
    }
}
