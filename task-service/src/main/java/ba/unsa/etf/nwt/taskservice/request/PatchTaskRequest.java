package ba.unsa.etf.nwt.taskservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchTaskRequest {
    @NotBlank(message = "Task name can't be blank")
    @Size(max = 50, message = "Task name can contain at most 50 characters")
    private JsonNullable<String> name = JsonNullable.undefined();

    @NotBlank(message = "Task description can't be blank")
    @Size(max = 255, message = "Task description can contain at most 255 characters")
    private JsonNullable<String> description = JsonNullable.undefined();

    @NotNull(message = "Project id can't be null")
    @JsonProperty("priority_id")
    private JsonNullable<UUID> priorityId = JsonNullable.undefined();

    @NotNull(message = "Type id can't be null")
    @JsonProperty("type_id")
    private JsonNullable<UUID> typeId = JsonNullable.undefined();

    @NotNull(message = "Status id can't be null")
    @JsonProperty("status_id")
    private JsonNullable<UUID> statusId = JsonNullable.undefined();

    @JsonProperty("user_id")
    private JsonNullable<UUID> userId = JsonNullable.undefined();
}
