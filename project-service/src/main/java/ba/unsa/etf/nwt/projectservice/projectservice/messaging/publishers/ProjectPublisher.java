package ba.unsa.etf.nwt.projectservice.projectservice.messaging.publishers;

import ba.unsa.etf.nwt.projectservice.projectservice.config.RabbitMQConfig;
import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.messaging.Publisher;
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
                RabbitMQConfig.DeleteProjectQueueConfig.EXCHANGE_NAME,
                RabbitMQConfig.DeleteProjectQueueConfig.ROUTING_KEY,
                data
        );
    }
}
