package ba.unsa.etf.nwt.emailservice.emailservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "email_subscriptions")
@Data
@NoArgsConstructor
public class EmailSubscription {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull(message = "Email configuration can't be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "config_id", nullable = false)
    private EmailConfig config;

    @Column(name = "task_id", nullable = false)
    @NotNull(message = "Task can't be null")
    private UUID task;
}
