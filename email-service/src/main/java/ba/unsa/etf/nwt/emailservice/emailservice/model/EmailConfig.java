package ba.unsa.etf.nwt.emailservice.emailservice.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "email_configs")
public class EmailConfig {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "config")
    private Set<EmailSubscription> emailSubscriptions;

}
