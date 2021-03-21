package ba.unsa.etf.nwt.userservice.response.base;

import ba.unsa.etf.nwt.userservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T extends Resource> {
    T data;
}
