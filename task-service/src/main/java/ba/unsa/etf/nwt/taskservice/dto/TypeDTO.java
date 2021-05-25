package ba.unsa.etf.nwt.taskservice.dto;

import ba.unsa.etf.nwt.taskservice.model.Type;
import ba.unsa.etf.nwt.taskservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDTO implements Resource {
    private UUID id;
    private String type;

    public TypeDTO(Type type) {
        this.id = type.getId();
        this.type = type.getType().toString();
    }
}
