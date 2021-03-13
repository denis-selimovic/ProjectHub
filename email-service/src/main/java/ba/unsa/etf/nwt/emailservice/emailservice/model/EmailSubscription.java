package ba.unsa.etf.nwt.emailservice.emailservice.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "email_subscriptions")
public class EmailSubscription {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "config_id", nullable = false)
    private EmailConfig config;

    @Column(name = "task_id", nullable = false)
    private UUID task;
}
