package ba.unsa.etf.nwt.emailservice.emailservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "email_configs")
@Data
@NoArgsConstructor
public class EmailConfig {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Email is invalid")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "user_id", nullable = false)
    @NotNull(message = "User id can't be null")
    private UUID userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "config")
    private Set<EmailSubscription> emailSubscriptions = new HashSet<>();;

}
