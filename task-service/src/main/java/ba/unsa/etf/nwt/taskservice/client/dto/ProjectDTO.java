package ba.unsa.etf.nwt.taskservice.client.dto;

import ba.unsa.etf.nwt.taskservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO implements Resource {
    private UUID projectId;
    private String name;
    private UUID ownerId;
    private Instant createdAt;
    private Instant updatedAt;
}
