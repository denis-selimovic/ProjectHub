package ba.unsa.etf.nwt.notificationservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "notification_user")
@Data
@NoArgsConstructor
public class NotificationUser {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull(message = "Notification id can't be null")
    @ManyToOne
    @JoinColumn(name = "notification_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Notification notification;

    @NotNull(message = "User id can't be null")
    @Column(name = "user_id", nullable = false)
    private UUID userId;
}
