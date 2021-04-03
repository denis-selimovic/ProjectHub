package ba.unsa.etf.nwt.emailservice.emailservice.dto;

import ba.unsa.etf.nwt.emailservice.emailservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO implements Resource {
    private String message;
    private String email;
}
