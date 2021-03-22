package ba.unsa.etf.nwt.taskservice.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class CreateIssueRequest {
    @NotBlank(message = "Issue name can't be blank")
    @Size(max = 50, message = "Issue name can contain at most 50 characters")
    @JsonProperty("name")
    private final String name;

    @NotBlank(message = "Issue description can't be blank")
    @Size(max = 255, message = "Issue description can contain at most 255 characters")
    @JsonProperty("description")
    private final String description;

    @NotNull(message = "Project id can't be null")
    @JsonProperty("project_id")
    private final UUID projectId;

    @NotNull(message = "Priority id can't be null")
    @JsonProperty("priority_id")
    private final UUID priorityId;
}
