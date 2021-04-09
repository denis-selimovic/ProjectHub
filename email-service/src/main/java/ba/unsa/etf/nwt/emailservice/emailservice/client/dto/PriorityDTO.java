package ba.unsa.etf.nwt.emailservice.emailservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PriorityDTO {
    private UUID id;
    private String priority;
}
