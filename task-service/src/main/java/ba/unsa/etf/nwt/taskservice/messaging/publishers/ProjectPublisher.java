package ba.unsa.etf.nwt.taskservice.messaging.publishers;

import ba.unsa.etf.nwt.taskservice.config.RabbitMQConfig;
import ba.unsa.etf.nwt.taskservice.dto.ProjectDTO;
import ba.unsa.etf.nwt.taskservice.messaging.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectPublisher implements Publisher<ProjectDTO> {

    private final RabbitTemplate template;

    @Override
    public void send(ProjectDTO data) {
        this.template.convertAndSend(
                RabbitMQConfig.RevertProjectDeleteQueueConfig.EXCHANGE_NAME,
                RabbitMQConfig.RevertProjectDeleteQueueConfig.ROUTING_KEY,
                data
        );
    }
}
