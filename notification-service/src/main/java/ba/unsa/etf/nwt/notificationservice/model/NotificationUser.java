package ba.unsa.etf.nwt.notificationservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
    @ToString.Exclude
    private Notification notification;

    @NotNull(message = "User id can't be null")
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NotNull(message = "Attribute read can't be null")
    @Column(name = "read", nullable = false)
    private Boolean read;
}
