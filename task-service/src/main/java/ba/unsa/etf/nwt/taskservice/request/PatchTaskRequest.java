package ba.unsa.etf.nwt.taskservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchTaskRequest {
    @NotNull
    private String name;
    @NotNull
    private String description;
}
