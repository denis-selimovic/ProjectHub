package ba.unsa.etf.nwt.userservice.logging;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class LogMessage {

    @Builder.Default
    private Instant timestamp = Instant.now();
    @Builder.Default
    private String service = "user-service";
    private String principal;
    private String method;
    private String action;
    private String resource;
    private String requestURL;
    private Integer status;
}
