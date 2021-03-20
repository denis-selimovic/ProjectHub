package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectCollaboratorDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.request.AddCollaboratorRequest;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.Response;
import ba.unsa.etf.nwt.projectservice.projectservice.security.ResourceOwner;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectCollaboratorService;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/collaborators")
@RequiredArgsConstructor
public class ProjectCollaboratorController {

    private final ProjectService projectService;
    private final ProjectCollaboratorService projectCollaboratorService;

    @PostMapping("/add/{projectId}")
    public ResponseEntity<Response> addCollaborator(@PathVariable UUID projectId, @RequestBody @Valid AddCollaboratorRequest request, ResourceOwner resourceOwner) {
        Project project = projectService.findById(projectId);

        if (!project.getOwnerId().equals(resourceOwner.getId()))
            throw new ForbiddenException("You don't have permission for this activity");

        ProjectCollaborator projectCollaborator = projectCollaboratorService.createCollaborator(request, project);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(new ProjectCollaboratorDTO(projectCollaborator)));
    }

//    @DeleteMapping("/{collaboratorId}")
//    public ResponseEntity<Response> deleteCollaborator(@PathVariable UUID collaboratorId, ResourceOwner resourceOwner) {
//        if (!projectCollaboratorService.existsById(collaboratorId))
//            throw new UnprocessableEntityException("Request body can not be processed. This collaborator doesn't exist");
//
//    }
}