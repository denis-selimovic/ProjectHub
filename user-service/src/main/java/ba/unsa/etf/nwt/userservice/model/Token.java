package ba.unsa.etf.nwt.userservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tokens")
@Data
@NoArgsConstructor
public class Token {

    public enum TokenType { ACTIVATE_ACCOUNT, RESET_PASSWORD }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @NotNull(message = "Type can't be null")
    private TokenType type;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "duration", nullable = false)
    @Positive(message = "Duration must be a positive integer")
    private Integer duration;

    @Column(name = "token", unique = true, nullable = false)
    @NotNull(message = "Token can't be null")
    @Size(min = 8, message = "Token must have at least 8 characters")
    private String token;

    @Column(name = "valid", nullable = false)
    @NotNull(message = "Valid flag can't be null")
    private Boolean valid = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Token must have a user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    public Boolean isExpired() {
        Instant expiryTime = createdAt.plusSeconds(duration);
        return Instant.now().compareTo(expiryTime) > 0 || !valid;
    }
}
