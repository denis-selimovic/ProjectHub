package ba.unsa.etf.nwt.userservice.response.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Map<String, String> errors = new HashMap<>();

    public void addError(final String message) {
        errors.put("message", message);
    }
}
