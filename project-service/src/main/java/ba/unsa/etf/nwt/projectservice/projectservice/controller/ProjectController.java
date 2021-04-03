package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.MetadataDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.filter.ProjectFilter;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.request.CreateProjectRequest;
import ba.unsa.etf.nwt.projectservice.projectservice.request.PatchProjectRequest;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectCollaboratorService projectCollaboratorService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Project created"),
            @ApiResponse(code = 422, message = "Unprocessable entity: Validation fail", response = ErrorResponse.class),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Response<ProjectDTO>> create(@RequestBody @Valid CreateProjectRequest request,
                                                       ResourceOwner resourceOwner) {
        Project project = projectService.create(request, resourceOwner);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(new ProjectDTO(project)));
    }

    @DeleteMapping("/{projectId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project deleted"),
            @ApiResponse(code = 403, message = "Forbidden: Only the project owner can delete project", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found: Project not found", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> delete(@PathVariable UUID projectId,
                                                           ResourceOwner resourceOwner) {
        Project project = projectService.findById(projectId);
        projectService.checkIfOwner(project.getOwnerId(), resourceOwner.getId());
        projectService.delete(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(new SimpleResponse("Project successfully deleted")));
    }

    @PatchMapping("/{projectId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project updated"),
            @ApiResponse(code = 404, message = "Project not found"),
            @ApiResponse(code = 403, message = "Forbidden: User not owner of project", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<ProjectDTO>> patch(ResourceOwner resourceOwner,
                                                      @PathVariable UUID projectId,
                                                      @RequestBody @Valid PatchProjectRequest patchProjectRequest) {
        Project project = projectService.findById(projectId);
        projectService.checkIfOwner(project.getOwnerId(), resourceOwner.getId());
        projectService.patch(project, patchProjectRequest);
        return ResponseEntity.ok().body(new Response<>(new ProjectDTO(project)));

    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 422, message = "Unprocessable entity: Invalid filter")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PaginatedResponse<ProjectDTO, MetadataDTO>> getFiltered(@RequestParam(required = false) String filter,
                                                                                  Pageable pageable,
                                                                                  ResourceOwner resourceOwner) {
        ProjectFilter projectFilter = new ProjectFilter(filter, resourceOwner.getId());
        Page<Project> projectPage = projectService.findByFilter(projectFilter, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PaginatedResponse<>(new MetadataDTO(projectPage),
                                projectPage
                                        .getContent()
                                        .stream()
                                        .map(ProjectDTO::new)
                                        .collect(Collectors.toList())
                        )
                );
    }

    @GetMapping("/{projectId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Project not found")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<ProjectDTO>> getProjectById(@PathVariable UUID projectId,
                                                               ResourceOwner resourceOwner) {
        Project project = projectService.findById(projectId);
        projectCollaboratorService.checkIfOwnerOrCollaborator(project.getOwnerId(), resourceOwner.getId(), project.getId());
        return ResponseEntity.ok().body(new Response<>(new ProjectDTO(project)));
    }
}
