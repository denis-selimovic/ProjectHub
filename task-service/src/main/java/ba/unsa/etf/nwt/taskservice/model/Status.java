package ba.unsa.etf.nwt.taskservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "statuses")
public class Status {

    public enum StatusType { OPEN, IN_PROGRESS, IN_REVIEW, IN_TESTING, DONE }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;
}
