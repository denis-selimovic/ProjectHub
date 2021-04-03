package ba.unsa.etf.nwt.userservice.client.dto;

import ba.unsa.etf.nwt.userservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO implements Resource {
    private String message;
    private String email;
}
