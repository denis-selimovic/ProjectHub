package ba.unsa.etf.nwt.taskservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "Task name can't be blank")
    @Size(max = 50, message = "Task name can contain at most 50 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Task description can't be blank")
    @Size(max = 255, message = "Task description can contain at most 255 characters")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "User id can't be null")
    @Column(name = "user_id")
    private UUID user_id;

    @NotNull(message = "Project id can't be null")
    @Column(name = "project_id", nullable = false)
    private UUID project_id;

    @NotNull(message = "Priority id can't be null")
    @OneToOne
    @JoinColumn(name = "priority_id", nullable = false)
    private Priority priority;

    @NotNull(message = "Status id can't be null")
    @OneToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @NotNull(message = "Type id can't be null")
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
