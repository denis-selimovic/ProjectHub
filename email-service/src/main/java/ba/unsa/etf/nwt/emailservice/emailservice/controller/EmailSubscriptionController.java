package ba.unsa.etf.nwt.emailservice.emailservice.controller;

import ba.unsa.etf.nwt.emailservice.emailservice.client.service.TaskService;
import ba.unsa.etf.nwt.emailservice.emailservice.dto.EmailSubscriptionDto;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailSubscription;
import ba.unsa.etf.nwt.emailservice.emailservice.request.CreateSubscriptionRequest;
import ba.unsa.etf.nwt.emailservice.emailservice.response.base.Response;
import ba.unsa.etf.nwt.emailservice.emailservice.security.ResourceOwner;
import ba.unsa.etf.nwt.emailservice.emailservice.service.EmailConfigService;
import ba.unsa.etf.nwt.emailservice.emailservice.service.EmailSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/email-subscriptions")
@RequiredArgsConstructor
public class EmailSubscriptionController {

    private final EmailConfigService emailConfigService;
    private final EmailSubscriptionService emailSubscriptionService;
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Response<EmailSubscriptionDto>> create(ResourceOwner resourceOwner,
                                                                 @RequestBody @Valid CreateSubscriptionRequest request) {
        EmailConfig config = emailConfigService.findByUserId(resourceOwner.getId());
        taskService.findTaskById(resourceOwner.getAuthHeader(), request.getTaskId());
        EmailSubscription subscription = emailSubscriptionService.create(request, config);
        return new ResponseEntity<>(new Response<>(new EmailSubscriptionDto(subscription)), HttpStatus.CREATED);
    }
}
