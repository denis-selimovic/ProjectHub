package ba.unsa.etf.nwt.notificationservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDTO {
    private UUID id;
    private String type;
}
