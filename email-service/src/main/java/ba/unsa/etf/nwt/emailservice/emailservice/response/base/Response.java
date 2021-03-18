package ba.unsa.etf.nwt.emailservice.emailservice.response.base;

import ba.unsa.etf.nwt.emailservice.emailservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    Resource data;
}
