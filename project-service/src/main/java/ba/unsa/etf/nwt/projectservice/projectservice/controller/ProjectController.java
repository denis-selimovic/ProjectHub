package ba.unsa.etf.nwt.projectservice.projectservice.controller;

import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectCollaboratorDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.dto.ProjectDTO;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import ba.unsa.etf.nwt.projectservice.projectservice.model.ProjectCollaborator;
import ba.unsa.etf.nwt.projectservice.projectservice.request.AddCollaboratorRequest;
import ba.unsa.etf.nwt.projectservice.projectservice.request.CreateProjectRequest;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.Response;
import ba.unsa.etf.nwt.projectservice.projectservice.response.base.SimpleResponse;
import ba.unsa.etf.nwt.projectservice.projectservice.security.ResourceOwner;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectCollaboratorService;
import ba.unsa.etf.nwt.projectservice.projectservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectCollaboratorService projectCollaboratorService;

    @PostMapping("/add")
    public ResponseEntity<Response> create(@RequestBody @Valid CreateProjectRequest request, ResourceOwner resourceOwner) {
        Project project = projectService.create(request, resourceOwner);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(new ProjectDTO(project)));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Response> delete(@PathVariable UUID projectId) {

        if (!projectService.existsById(projectId))
            throw new UnprocessableEntityException("Request body can not be processed. This project doesn't exist");
        projectService.delete(projectId);
        // todo vidjeti sta se desava sa kolaboratorima
        return ResponseEntity.status(HttpStatus.OK).body(new Response(new SimpleResponse("Project successfully deleted")));
    }

}
