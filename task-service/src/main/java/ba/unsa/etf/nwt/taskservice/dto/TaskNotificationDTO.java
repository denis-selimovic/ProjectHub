package ba.unsa.etf.nwt.taskservice.dto;

import ba.unsa.etf.nwt.taskservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskNotificationDTO implements Resource {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Change {
        public String previous;
        public String current;
    }

    private final Map<String, Change> changes = new HashMap<>();

    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("task_id")
    private UUID taskId;
    @JsonProperty("task_name")
    private String taskName;

    public void addChange(String property, String prev, String curr) {
        changes.put(property, new Change(prev, curr));
    }
}
