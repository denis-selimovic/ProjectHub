package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectCollaboratorDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.request.AddCollaboratorRequest;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.Response;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.SimpleResponse;
import ba.unsa.etf.nwt.projectservice.projectservice.security.ResourceOwner;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectCollaboratorService;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/collaborators")
@RequiredArgsConstructor
public class ProjectCollaboratorController {

    private final ProjectService projectService;
    private final ProjectCollaboratorService projectCollaboratorService;

    @PostMapping("/add")
    public ResponseEntity<Response> addCollaborator(@RequestBody @Valid AddCollaboratorRequest request, ResourceOwner resourceOwner) {
        Project project = projectService.findById(request.getProjectId());

        if (!project.getOwnerId().equals(resourceOwner.getId()))
            throw new ForbiddenException("You don't have permission for this activity");

        ProjectCollaborator projectCollaborator = projectCollaboratorService.createCollaborator(request.getCollaboratorId(), project);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(new ProjectCollaboratorDTO(projectCollaborator)));
    }

    @DeleteMapping("/{collaboratorId}")
    public ResponseEntity<Response> deleteCollaborator(@PathVariable UUID collaboratorId, ResourceOwner resourceOwner) {
        ProjectCollaborator projectCollaborator = projectCollaboratorService.findById(collaboratorId);

        if (!projectCollaborator.getProject().getOwnerId().equals(resourceOwner.getId()))
            throw new ForbiddenException("You don't have permission for this activity");

        projectCollaboratorService.delete(projectCollaborator);
        return ResponseEntity.status(HttpStatus.OK).body(new Response(new SimpleResponse("Project collaborator successfully deleted")));
    }

//    @GetMapping("/all/{projectId}")
//    public ResponseEntity<Response> allCollaboratorsOnProject(@PathVariable UUID projectId) {
//      return null;
//    }
}