package ba.unsa.etf.nwt.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("errors")
    private Map<String, String> errors;
}
