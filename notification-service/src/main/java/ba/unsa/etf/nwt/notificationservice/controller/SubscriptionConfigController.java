package ba.unsa.etf.nwt.notificationservice.controller;

import ba.unsa.etf.nwt.notificationservice.dto.SubscriptionConfigDTO;
import ba.unsa.etf.nwt.notificationservice.model.SubscriptionConfig;
import ba.unsa.etf.nwt.notificationservice.request.SubscriptionConfigRequest;
import ba.unsa.etf.nwt.notificationservice.response.base.ErrorResponse;
import ba.unsa.etf.nwt.notificationservice.response.base.Response;
import ba.unsa.etf.nwt.notificationservice.service.SubscriptionConfigService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/subscription-config")
@RequiredArgsConstructor
public class SubscriptionConfigController {
    private final SubscriptionConfigService subscriptionConfigService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Subscription config created"),
            @ApiResponse(code = 422, message = "Unprocessable entity: Validation fail or config already exists",
                    response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Response<SubscriptionConfigDTO>> create(@Valid @RequestBody SubscriptionConfigRequest request) {
        SubscriptionConfig config = subscriptionConfigService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(new SubscriptionConfigDTO(config)));
    }
}
