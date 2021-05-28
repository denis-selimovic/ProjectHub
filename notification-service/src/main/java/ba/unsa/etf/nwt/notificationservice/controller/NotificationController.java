package ba.unsa.etf.nwt.notificationservice.controller;

import ba.unsa.etf.nwt.notificationservice.dto.MetadataDTO;
import ba.unsa.etf.nwt.notificationservice.dto.NotificationDTO;
import ba.unsa.etf.nwt.notificationservice.model.Notification;
import ba.unsa.etf.nwt.notificationservice.model.NotificationUser;
import ba.unsa.etf.nwt.notificationservice.request.CreateNotificationRequest;
import ba.unsa.etf.nwt.notificationservice.request.PatchNotificationRequest;
import ba.unsa.etf.nwt.notificationservice.response.base.ErrorResponse;
import ba.unsa.etf.nwt.notificationservice.response.base.PaginatedResponse;
import ba.unsa.etf.nwt.notificationservice.response.base.Response;
import ba.unsa.etf.nwt.notificationservice.response.base.SimpleResponse;
import ba.unsa.etf.nwt.notificationservice.security.ResourceOwner;
import ba.unsa.etf.nwt.notificationservice.service.NotificationService;
import ba.unsa.etf.nwt.notificationservice.service.NotificationUserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationUserService notificationUserService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Notification created"),
            @ApiResponse(code = 422, message = "Unprocessable entity: Validation fail", response = ErrorResponse.class),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Response<NotificationDTO>> create(@RequestBody @Valid CreateNotificationRequest request, ResourceOwner resourceOwner) {
        NotificationDTO notification = notificationService.create(request, resourceOwner);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(notification));
    }


    @DeleteMapping("/{notificationId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Notification deleted"),
            @ApiResponse(code = 404, message = "Not found: Notification not found", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> delete(@PathVariable UUID notificationId, ResourceOwner resourceOwner) {
        notificationService.delete(notificationId, resourceOwner.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(new SimpleResponse("Notification successfully deleted")));
    }


    @GetMapping
    @ApiResponse(code = 200, message = "Success")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PaginatedResponse<NotificationDTO, MetadataDTO>> getNotifications(ResourceOwner resourceOwner,
                                                                                            Pageable pageable,
                                                                                            @RequestParam(required = false) Boolean read) {
        Page<NotificationDTO> notifications = notificationService
                .getNotificationsForUser(resourceOwner.getId(), read, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new PaginatedResponse<>(new MetadataDTO(notifications), notifications.getContent()));
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
        NotificationUser notificationUser = notificationUserService.findByNotificationAndUserId(notification, resourceOwner.getId());
        notificationUser = notificationUserService.patch(notificationUser, patchNotificationRequest);
        NotificationDTO dto = new NotificationDTO(notification, notificationUser.getRead());
        return ResponseEntity.ok().body(new Response<>(dto));
    }
}
