package ba.unsa.etf.nwt.taskservice.service;

import ba.unsa.etf.nwt.taskservice.dto.TaskDTO;
import ba.unsa.etf.nwt.taskservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.taskservice.exception.base.UnpocessableEntityException;
import ba.unsa.etf.nwt.taskservice.filter.GenericSpecificationBuilder;
import ba.unsa.etf.nwt.taskservice.filter.SearchCriteria;
import ba.unsa.etf.nwt.taskservice.model.Priority;
import ba.unsa.etf.nwt.taskservice.model.Status;
import ba.unsa.etf.nwt.taskservice.model.Task;
import ba.unsa.etf.nwt.taskservice.model.Type;
import ba.unsa.etf.nwt.taskservice.repository.TaskRepository;
import ba.unsa.etf.nwt.taskservice.request.CreateTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Root;
import java.util.*;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final GenericSpecificationBuilder<Task> builder = new GenericSpecificationBuilder<>();

    private final TaskRepository taskRepository;
    private final PriorityService priorityService;
    private final StatusService statusService;
    private final TypeService typeService;

    public Task create(final CreateTaskRequest request) {
        taskRepository.findByNameAndProjectId(request.getName(), request.getProjectId()).ifPresent(t -> {
            throw new UnpocessableEntityException("Task with this name already exists");
        });
        Task task = createTaskFromRequest(request);
        return taskRepository.save(task);
    }

    private Task createTaskFromRequest(final CreateTaskRequest request) {
        Priority priority =  priorityService.findById(request.getPriorityId());
        Status status = statusService.findByStatusType(Status.StatusType.OPEN);
        Type type = typeService.findById(request.getTypeId());

        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setUserId(request.getUserId());
        task.setProjectId(request.getProjectId());
        task.setPriority(priority);
        task.setStatus(status);
        task.setType(type);
        return task;
    }

    public void delete(final Task task) {
        taskRepository.delete(task);
    }

    public Task findById(final UUID taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> {
            throw new NotFoundException("Task not found");
        });
    }

    public Page<TaskDTO> filter(Pageable pageable, UUID projectId, String priorityId, String statusId, String typeId) {
        Specification<Task> specification = builder
                .with(new String[]{"projectId"}, projectId.toString(), SearchCriteria.SearchCriteriaOperation.EQ, UUID::fromString)
                .with(new String[]{"priority", "id"}, priorityId, SearchCriteria.SearchCriteriaOperation.EQ, UUID::fromString)
                .with(new String[]{"status", "id"}, statusId, SearchCriteria.SearchCriteriaOperation.EQ, UUID::fromString)
                .with(new String[]{"type", "id"}, typeId, SearchCriteria.SearchCriteriaOperation.EQ, UUID::fromString)
                .build();
        return taskRepository.findAll(specification, pageable);
    }
}
