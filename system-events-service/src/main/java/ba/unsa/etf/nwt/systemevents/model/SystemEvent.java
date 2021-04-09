package ba.unsa.etf.nwt.systemevents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "system_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemEvent {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "timestamp")
    private Instant timestamp;

    @Column(name = "service")
    private String service;

    @Column(name = "principal")
    private String principal;

    @Column(name = "method")
    private String method;

    @Column(name = "action")
    private String action;

    @Column(name = "resource")
    private String resource;

    @Column(name = "request_url")
    private String requestURL;

    @Column(name = "status")
    private Integer status;

    public SystemEvent(Instant timestamp, String service, String principal, String method,
                       String action, String resource, String requestURL, int status) {
        this.timestamp = timestamp;
        this.service = service;
        this.principal = principal;
        this.method = method;
        this.action = action;
        this.resource = resource;
        this.requestURL = requestURL;
        this.status = status;
    }
}
