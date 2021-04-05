package ba.unsa.etf.nwt.notificationservice.client.dto;

import ba.unsa.etf.nwt.notificationservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO implements Resource {
    private UUID id;
    private String name;
    private String description;
    private UUID userId;
    private UUID projectId;
    private PriorityDTO priority;
    private StatusDTO status;
    private TypeDTO type;
    private Instant createdAt;
    private Instant updatedAt;
}
