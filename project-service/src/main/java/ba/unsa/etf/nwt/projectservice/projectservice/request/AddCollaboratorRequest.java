package ba.unsa.etf.nwt.projectservice.projectservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class AddCollaboratorRequest {

    @NotNull(message = "Collaborator id can't be null")
    @JsonProperty("collaborator_id")
    private UUID collaboratorId;
}
