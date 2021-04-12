package ba.unsa.etf.nwt.notificationservice.client.service;

import ba.unsa.etf.nwt.notificationservice.client.TaskServiceClient;
import ba.unsa.etf.nwt.notificationservice.client.dto.TaskDTO;
import ba.unsa.etf.nwt.notificationservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
   private final TaskServiceClient taskServiceClient;

   public TaskDTO findTaskById(ResourceOwner resourceOwner, UUID taskId) {
       return Objects.requireNonNull(taskServiceClient
               .getTaskById(resourceOwner.getAuthHeader(), taskId)
               .getBody())
               .getData();
   }
}
