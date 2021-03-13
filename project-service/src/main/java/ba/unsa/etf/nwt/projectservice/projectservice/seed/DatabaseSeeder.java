package ba.unsa.etf.nwt.projectservice.projectservice.seed;

import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final ProjectService projectService;
    private final ResourceLoader resourceLoader;

    @EventListener
    public void seed(final ContextRefreshedEvent event) throws IOException {
        seedProjectsTable();
    }

    private void seedProjectsTable() throws IOException {
        List<Project> existingProjects = projectService.findAll();
        if (existingProjects.isEmpty()) {
            List<UUID> userUuids = getUuids("/uuids/user_uuids.txt");

            Project p1 = new Project("Microservices Project", userUuids.get(0));
            Project p2 = new Project("Reddit clone", userUuids.get(1));
            Project p3 = new Project("Zamger API", userUuids.get(2));

            projectService.saveProject(p1);
            projectService.saveProject(p2);
            projectService.saveProject(p3);

            seedProjectCollaboratorsTable(List.of(p1, p2, p3), userUuids);
        } else {
            System.out.println("Seeding is not required");
        }
    }

    private void seedProjectCollaboratorsTable(List<Project> projects, final List<UUID> userUuids) {
        projects.get(0).addCollaborator(new ProjectCollaborator(projects.get(0), userUuids.get(3)));
        projects.get(0).addCollaborator(new ProjectCollaborator(projects.get(0), userUuids.get(4)));
        projects.get(0).addCollaborator(new ProjectCollaborator(projects.get(0), userUuids.get(5)));

        System.out.printf("Adding collaborators to project with id %s:\n%s\n%s\n%s.%n",
                projects.get(0).getId(),
                userUuids.get(3),
                userUuids.get(4),
                userUuids.get(5)
        );

        projects.get(1).addCollaborator(new ProjectCollaborator(projects.get(1), userUuids.get(3)));
        projects.get(1).addCollaborator(new ProjectCollaborator(projects.get(1), userUuids.get(6)));
        projects.get(1).addCollaborator(new ProjectCollaborator(projects.get(1), userUuids.get(7)));

        System.out.printf("Adding collaborators to project with id %s:\n%s\n%s\n%s.\n",
                projects.get(1).getId(),
                userUuids.get(3),
                userUuids.get(6),
                userUuids.get(7)
        );

        projects.get(2).addCollaborator(new ProjectCollaborator(projects.get(2), userUuids.get(6)));
        projects.get(2).addCollaborator(new ProjectCollaborator(projects.get(2), userUuids.get(7)));
        projects.get(2).addCollaborator(new ProjectCollaborator(projects.get(2), userUuids.get(8)));
        projects.get(2).addCollaborator(new ProjectCollaborator(projects.get(2), userUuids.get(9)));

        System.out.printf("Adding collaborators to project with id %s:\n%s\n%s\n%s\n%s.\n",
                projects.get(2).getId(),
                userUuids.get(6),
                userUuids.get(7),
                userUuids.get(8),
                userUuids.get(9)
        );

        projects.forEach(projectService::saveProject);
    }

    private List<UUID> getUuids(String fileName) throws IOException {
        File file = resourceLoader.getResource(String.format("classpath:%s", fileName)).getFile();
        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(fileReader);
        List<String> uuids = new ArrayList<>();
        while (scanner.hasNextLine()) {
            uuids.add(scanner.nextLine());
        }

        return uuids.stream().map(UUID::fromString).collect(Collectors.toList());
    }
}
