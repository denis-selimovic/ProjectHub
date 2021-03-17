package ba.unsa.etf.nwt.userservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tokens")
@Data
@NoArgsConstructor
public class Token {

    public enum TokenType { RESET_PASSWORD, ACTIVATE_ACCOUNT }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TokenType type;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "valid", nullable = false)
    private Boolean valid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
