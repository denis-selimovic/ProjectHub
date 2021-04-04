package ba.unsa.etf.nwt.emailservice.emailservice.controller;

import ba.unsa.etf.nwt.emailservice.emailservice.dto.EmailConfigDto;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.request.CreateConfigRequest;
import ba.unsa.etf.nwt.emailservice.emailservice.response.base.Response;
import ba.unsa.etf.nwt.emailservice.emailservice.service.EmailConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/email-config")
@RequiredArgsConstructor
public class EmailConfigController {

    private final EmailConfigService emailConfigService;

    @PostMapping
    public ResponseEntity<Response<EmailConfigDto>> create(@RequestBody @Valid CreateConfigRequest request) {
        EmailConfig config = emailConfigService.create(request);
        return new ResponseEntity<>(new Response<>(new EmailConfigDto(config)), HttpStatus.CREATED);
    }
}
