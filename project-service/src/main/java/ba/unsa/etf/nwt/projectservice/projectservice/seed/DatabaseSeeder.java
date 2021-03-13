package ba.unsa.etf.nwt.projectservice.projectservice.seed;

import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final ProjectService projectService;

    @EventListener
    public void seed(final ContextRefreshedEvent event) {
        seedProjectsTable();
    }

    private void seedProjectsTable() {
        List<Project> existingProjects = projectService.findAll();
        if (existingProjects.isEmpty()) {
            Project p1 = new Project("Microservices Project", UUID.randomUUID());
            Project p2 = new Project("Reddit clone", UUID.randomUUID());
            Project p3 = new Project("Zamger API", UUID.randomUUID());

            projectService.saveProject(p1);
            projectService.saveProject(p2);
            projectService.saveProject(p3);

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

        projects.get(0).addCollaborator(new ProjectCollaborator(projects.get(0), collaborator1));
        projects.get(0).addCollaborator(new ProjectCollaborator(projects.get(0), collaborator2));
        projects.get(0).addCollaborator(new ProjectCollaborator(projects.get(0), collaborator3));

        projects.get(1).addCollaborator(new ProjectCollaborator(projects.get(1), collaborator1));
        projects.get(1).addCollaborator(new ProjectCollaborator(projects.get(1), collaborator4));
        projects.get(1).addCollaborator(new ProjectCollaborator(projects.get(1), collaborator5));

        projects.get(2).addCollaborator(new ProjectCollaborator(projects.get(2), collaborator3));
        projects.get(2).addCollaborator(new ProjectCollaborator(projects.get(2), collaborator4));
        projects.get(2).addCollaborator(new ProjectCollaborator(projects.get(2), collaborator6));
        projects.get(2).addCollaborator(new ProjectCollaborator(projects.get(2), collaborator7));

        projects.forEach(projectService::saveProject);
    }
}
