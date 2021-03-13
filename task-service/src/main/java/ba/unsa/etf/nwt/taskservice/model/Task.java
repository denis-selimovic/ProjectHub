package ba.unsa.etf.nwt.taskservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "user_id", nullable = false)
    private UUID user_id;

    @Column(name = "project_id", nullable = false)
    private UUID project_id;

    @OneToOne
    @JoinColumn(name = "priority_id", nullable = false)
    private Priority priority;

    @OneToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @OneToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private Set<Comment> comments;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
