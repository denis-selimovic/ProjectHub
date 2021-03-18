package ba.unsa.etf.nwt.userservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @JsonProperty("errors")
    private Map<String, String> errors = new HashMap<>();

    public void addError(final String message) {
        errors.put("message", message);
    }
}
