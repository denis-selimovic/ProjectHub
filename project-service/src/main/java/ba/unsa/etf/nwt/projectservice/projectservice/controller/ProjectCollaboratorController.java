package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.MetadataDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectCollaboratorDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.request.AddCollaboratorRequest;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.ErrorResponse;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.PaginatedResponse;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.Response;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.SimpleResponse;
import ba.unsa.etf.nwt.projectservice.projectservice.security.ResourceOwner;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectCollaboratorService;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/collaborators")
@RequiredArgsConstructor
public class ProjectCollaboratorController {

    private final ProjectService projectService;
    private final ProjectCollaboratorService projectCollaboratorService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Project collaborator created"),
            @ApiResponse(code = 422, message = "Unprocessable entity: Validation fail", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden: User not owner of the project", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Response<ProjectCollaboratorDTO>> create(@PathVariable UUID projectId,
                                           @RequestBody @Valid AddCollaboratorRequest request,
                                           ResourceOwner resourceOwner) {
        Project project = projectService.findById(projectId);
        if (!project.getOwnerId().equals(resourceOwner.getId()))
            throw new ForbiddenException("You don't have permission for this activity");

        ProjectCollaborator projectCollaborator = projectCollaboratorService.createCollaborator(request.getCollaboratorId(), project);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(new ProjectCollaboratorDTO(projectCollaborator)));
    }

    @DeleteMapping("/{collaboratorId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project collaborator deleted"),
            @ApiResponse(code = 403, message = "Forbidden: Only the owner can delete the project collaborator", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found: Project collaborator not found", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> delete(@PathVariable UUID projectId,
                                           @PathVariable UUID collaboratorId,
                                           ResourceOwner resourceOwner) {
        ProjectCollaborator projectCollaborator = projectCollaboratorService
                .findByIdAndAndProjectId(collaboratorId, projectId);
        if (!projectCollaborator.getProject().getOwnerId().equals(resourceOwner.getId()))
            throw new ForbiddenException("You don't have permission for this activity");

        projectCollaboratorService.delete(projectCollaborator);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(new SimpleResponse("Project collaborator successfully deleted")));
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Forbidden: User not collaborator or owner of project", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found: Project not found", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PaginatedResponse<ProjectCollaboratorDTO, MetadataDTO>> getCollaboratorsForProject(@PathVariable UUID projectId,
                                                                        ResourceOwner resourceOwner,
                                                                        Pageable pageable) {
        Project project = projectService.findById(projectId);
        if (!project.getOwnerId().equals(resourceOwner.getId()) &&
                projectCollaboratorService.existsByCollaboratorIdAndProjectId(resourceOwner.getId(), projectId))
            throw new ForbiddenException("You don't have permission for this activity");

        Page<ProjectCollaboratorDTO> collaboratorPage = projectCollaboratorService
                .getCollaboratorsForProject(project, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PaginatedResponse<>(new MetadataDTO(collaboratorPage), collaboratorPage.getContent()));

    }
}