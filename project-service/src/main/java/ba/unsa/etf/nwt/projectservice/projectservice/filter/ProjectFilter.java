package ba.unsa.etf.nwt.projectservice.projectservice.filter;

import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.UnprocessableEntityException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFilter {
    private UUID ownerId;
    private UUID collaboratorId;

    public ProjectFilter(final String filter, final UUID userId) {
        if(filter == null){
            throw new UnprocessableEntityException("Invalid filter");
        }else if(filter.toLowerCase().equals("owner")) {
            this.ownerId = userId;
        }else if(filter.toLowerCase().equals("collaborator")) {
            this.collaboratorId = userId;
        }else {
            throw new UnprocessableEntityException("Invalid filter");
        }
    }
}
