package ba.unsa.etf.nwt.emailservice.emailservice.client.dto;

import ba.unsa.etf.nwt.emailservice.emailservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;


@Data
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
