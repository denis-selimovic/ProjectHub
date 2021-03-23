package ba.unsa.etf.nwt.notificationservice.controller;

import ba.unsa.etf.nwt.notificationservice.dto.MetadataDTO;
import ba.unsa.etf.nwt.notificationservice.dto.NotificationDTO;
import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.request.CreateNotificationRequest;
import ba.unsa.etf.nwt.notificationservice.request.PatchNotificationRequest;
import ba.unsa.etf.nwt.notificationservice.response.base.ErrorResponse;
import ba.unsa.etf.nwt.notificationservice.response.base.PaginatedResponse;
import ba.unsa.etf.nwt.notificationservice.response.base.Response;
import ba.unsa.etf.nwt.notificationservice.response.base.SimpleResponse;
import ba.unsa.etf.nwt.notificationservice.security.ResourceOwner;
import ba.unsa.etf.nwt.notificationservice.service.NotificationService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Notification created"),
            @ApiResponse(code = 422, message = "Unprocessable entity: Validation fail", response = ErrorResponse.class),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Response<NotificationDTO>> create(@RequestBody @Valid CreateNotificationRequest request, ResourceOwner resourceOwner) {
        Notification notification = notificationService.create(request, resourceOwner);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(new NotificationDTO(notification)));
    }


    @DeleteMapping("/{notificationId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Notification deleted"),
            @ApiResponse(code = 404, message = "Not found: Notification not found", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> delete(@PathVariable UUID notificationId) {

        if(!notificationService.existsById(notificationId))
            throw new UnprocessableEntityException("Request body can not be processed. This notification doesn't exist");

        notificationService.deleteById(notificationId);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(new SimpleResponse("Notification successfully deleted")));
    }


    @GetMapping
    @ApiResponse(code = 200, message = "Success")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PaginatedResponse<NotificationDTO, MetadataDTO>> getNotifications(ResourceOwner resourceOwner,
                                                                                            Pageable pageable) {

        Page<NotificationDTO> notificationPage = notificationService.getNotificationsForUser(
                                                                            resourceOwner.getId(),
                                                                            PageRequest.of(pageable.getPageNumber(),
                                                                            pageable.getPageSize(),
                                                                            Sort.by("createdAt").descending()));

        return ResponseEntity.ok(new PaginatedResponse<>(new MetadataDTO(notificationPage), notificationPage.getContent()));
    }

    @PatchMapping("/{notificationId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Notification updated"),
            @ApiResponse(code = 404, message = "Notification not found"),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<NotificationDTO>> patch(ResourceOwner resourceOwner,
                                                           @PathVariable UUID notificationId,
                                                           @RequestBody @Valid PatchNotificationRequest patchNotificationRequest) {

        Notification notification = notificationService.findById(notificationId);
        notificationService.checkUserId(resourceOwner.getId(), notification.getUserId());
        notificationService.patch(notification, patchNotificationRequest);

        return ResponseEntity.ok().body(new Response<>(new NotificationDTO(notification)));
    }
}
