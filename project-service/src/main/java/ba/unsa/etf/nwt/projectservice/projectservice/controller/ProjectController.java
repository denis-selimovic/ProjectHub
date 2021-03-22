package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.MetadataDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.projectservice.projectservice.filter.ProjectFilter;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.request.CreateProjectRequest;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.ErrorResponse;
import ba.unsa.etf.nwt.projectservice.projectservice.filter.ProjectFilter;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.PaginatedResponse;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.Response;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.SimpleResponse;
import ba.unsa.etf.nwt.projectservice.projectservice.security.ResourceOwner;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

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
        if (!project.getOwnerId().equals(resourceOwner.getId()))
            throw new ForbiddenException("You don't have permission for this activity");

        projectService.delete(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(new SimpleResponse("Project successfully deleted")));
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
}
