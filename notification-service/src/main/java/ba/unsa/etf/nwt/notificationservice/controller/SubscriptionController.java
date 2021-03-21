package ba.unsa.etf.nwt.notificationservice.controller;

import ba.unsa.etf.nwt.notificationservice.dto.NotificationDTO;
import ba.unsa.etf.nwt.notificationservice.dto.SubscriptionDTO;
import ba.unsa.etf.nwt.notificationservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.notificationservice.model.Subscription;
import ba.unsa.etf.nwt.notificationservice.request.CreateSubscriptionRequest;
import ba.unsa.etf.nwt.notificationservice.response.base.Response;
import ba.unsa.etf.nwt.notificationservice.response.base.SimpleResponse;
import ba.unsa.etf.nwt.notificationservice.security.ResourceOwner;
import ba.unsa.etf.nwt.notificationservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody @Valid CreateSubscriptionRequest request, ResourceOwner resourceOwner) {
        Subscription subscription = subscriptionService.create(request, resourceOwner);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(new SubscriptionDTO(subscription)));
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Response> delete(@PathVariable UUID subscriptionId) {

        if(!subscriptionService.existsById(subscriptionId))
            throw new UnprocessableEntityException("Request body can not be processed. This subscription doesn't exist");

        subscriptionService.deleteById(subscriptionId);

        return ResponseEntity.status(HttpStatus.OK).body(new Response(new SimpleResponse("Subscription successfully deleted")));
    }
}
