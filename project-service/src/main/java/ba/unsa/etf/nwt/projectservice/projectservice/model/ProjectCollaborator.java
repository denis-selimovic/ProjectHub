package ba.unsa.etf.nwt.projectservice.projectservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "project_collaborators")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCollaborator {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Project project;

    @Column(name = "collaborator_id", nullable = false)
    private UUID collaboratorId;

    public ProjectCollaborator(final Project project, final UUID collaboratorId) {
        this.project = project;
        this.collaboratorId = collaboratorId;
    }
}
