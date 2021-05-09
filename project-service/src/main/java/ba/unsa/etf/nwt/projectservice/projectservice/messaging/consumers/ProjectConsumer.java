package ba.unsa.etf.nwt.projectservice.projectservice.messaging.consumers;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.messaging.Consumer;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectConsumer implements Consumer<ProjectDTO> {

    private final ProjectRepository projectRepository;

    @RabbitListener(queues = "deleting-queue")
    public void receive(ProjectDTO data) {
        projectRepository.find(data.getId()).ifPresent(p -> {
            p.setDeleted(false);
            projectRepository.save(p);
        });
    }
}
