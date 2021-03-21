package ba.unsa.etf.nwt.taskservice.response.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Map<String, List<String>> errors = new HashMap<>();

    public void addError(final String message) {
        errors.put("message", Collections.singletonList(message));
    }
}
