package ba.unsa.etf.nwt.taskservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "types")
@Data
@NoArgsConstructor
public class Type {

    public enum TaskType { SPIKE, BUG, EPIC, STORY, CHANGE }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaskType type;
}
