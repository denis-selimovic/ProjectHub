package ba.unsa.etf.nwt.taskservice.dto;

import ba.unsa.etf.nwt.taskservice.model.Priority;
import ba.unsa.etf.nwt.taskservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriorityDTO implements Resource {
    private UUID id;
    private String priority;

    public PriorityDTO(Priority priority) {
        this.id = priority.getId();
        this.priority = priority.getPriorityType().toString();
    }
}
