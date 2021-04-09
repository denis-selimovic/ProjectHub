package ba.unsa.etf.nwt.emailservice.emailservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TypeDTO {

    private UUID id;
    private String type;
}
