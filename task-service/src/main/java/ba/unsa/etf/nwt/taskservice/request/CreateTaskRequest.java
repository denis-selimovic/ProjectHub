package ba.unsa.etf.nwt.taskservice.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class CreateTaskRequest {
    @NotBlank(message = "Task name can't be blank")
    @Size(max = 50, message = "Task name can contain at most 50 characters")
    @JsonProperty("name")
    private final String name;

    @NotBlank(message = "Task description can't be blank")
    @Size(max = 255, message = "Task description can contain at most 255 characters")
    @JsonProperty("description")
    private final String description;

    @JsonProperty("user_id")
    private final UUID userId;

    @NotNull(message = "Project id can't be null")
    @JsonProperty("project_id")
    private final UUID projectId;

    @NotNull(message = "Priority id can't be null")
    @JsonProperty("priority_id")
    private final UUID priorityId;

    @NotNull(message = "Type id can't be null")
    @JsonProperty("type_id")
    private final UUID typeId;
}
