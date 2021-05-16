package ba.unsa.etf.nwt.projectservice.projectservice.messaging.publishers;

import ba.unsa.etf.nwt.projectservice.projectservice.config.RabbitMQConfig;
import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectNotificationDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.messaging.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectNotificationPublisher implements Publisher<ProjectNotificationDTO> {

    private final RabbitTemplate template;

    @Override
    public void send(ProjectNotificationDTO data) {
        this.template.convertAndSend(
                RabbitMQConfig.ProjectNotificationQueueConfig.EXCHANGE_NAME,
                RabbitMQConfig.ProjectNotificationQueueConfig.ROUTING_KEY,
                data
        );
    }
}
