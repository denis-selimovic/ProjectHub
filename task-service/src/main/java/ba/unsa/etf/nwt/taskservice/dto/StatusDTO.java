package ba.unsa.etf.nwt.taskservice.dto;

import ba.unsa.etf.nwt.taskservice.model.Status;
import ba.unsa.etf.nwt.taskservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO implements Resource {
    private UUID id;
    private String status;

    public StatusDTO(Status status) {
        this.id = status.getId();
        this.status = status.getStatus().toString();
    }
}
