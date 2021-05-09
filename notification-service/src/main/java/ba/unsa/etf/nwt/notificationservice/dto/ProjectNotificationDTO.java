package ba.unsa.etf.nwt.notificationservice.dto;

import ba.unsa.etf.nwt.notificationservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectNotificationDTO implements Resource {

    private String title;
    private String description;
    private UUID userId;
}
