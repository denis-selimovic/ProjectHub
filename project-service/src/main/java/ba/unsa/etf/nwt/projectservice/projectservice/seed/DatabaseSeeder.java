package ba.unsa.etf.nwt.projectservice.projectservice.seed;

import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectCollaboratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final ProjectRepository projectRepository;
    private final ProjectCollaboratorRepository projectCollaboratorRepository;

    @EventListener
    public void seed(final ContextRefreshedEvent event) {
        seedProjectsTable();
    }

    private void seedProjectsTable() {
        if (projectRepository.count() == 0) {
            Project p1 = createProject("Microservices Project", UUID.randomUUID());
            Project p2 = createProject("Reddit clone", UUID.randomUUID());
            Project p3 = createProject("Zamger API", UUID.randomUUID());

            projectRepository.save(p1);
            projectRepository.save(p2);
            projectRepository.save(p3);

            seedProjectCollaboratorsTable(List.of(p1, p2, p3));
        } else {
            System.out.println("Seeding is not required");
        }
    }

    private void seedProjectCollaboratorsTable(List<Project> projects) {
        UUID collaborator1 = UUID.randomUUID();
        UUID collaborator2 = UUID.randomUUID();
        UUID collaborator3 = UUID.randomUUID();
        UUID collaborator4 = UUID.randomUUID();
        UUID collaborator5 = UUID.randomUUID();
        UUID collaborator6 = UUID.randomUUID();
        UUID collaborator7 = UUID.randomUUID();

        projectCollaboratorRepository.save(createProjectCollaborator(projects.get(0), collaborator1));
        projectCollaboratorRepository.save(createProjectCollaborator(projects.get(0), collaborator2));
        projectCollaboratorRepository.save(createProjectCollaborator(projects.get(0), collaborator3));
        projectCollaboratorRepository.save(createProjectCollaborator(projects.get(1), collaborator1));
        projectCollaboratorRepository.save(createProjectCollaborator(projects.get(1), collaborator4));
        projectCollaboratorRepository.save(createProjectCollaborator(projects.get(1), collaborator5));
        projectCollaboratorRepository.save(createProjectCollaborator(projects.get(2), collaborator3));
        projectCollaboratorRepository.save(createProjectCollaborator(projects.get(2), collaborator4));
        projectCollaboratorRepository.save(createProjectCollaborator(projects.get(2), collaborator6));
        projectCollaboratorRepository.save(createProjectCollaborator(projects.get(2), collaborator7));
    }

    private Project createProject(final String name, final UUID ownerId) {
        Project project = new Project();
        project.setName(name);
        project.setOwnerId(ownerId);
        return project;
    }

    private ProjectCollaborator createProjectCollaborator(final Project project, final UUID collaboratorId) {
        ProjectCollaborator projectCollaborator = new ProjectCollaborator();
        projectCollaborator.setProject(project);
        projectCollaborator.setCollaboratorId(collaboratorId);
        return projectCollaborator;
    }
}
