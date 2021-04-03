package ba.unsa.etf.nwt.userservice.client;

import ba.unsa.etf.nwt.userservice.client.dto.EmailDTO;
import ba.unsa.etf.nwt.userservice.client.request.SendEmailRequest;
import ba.unsa.etf.nwt.userservice.response.base.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value = "email-service")
@Component
public interface EmailServiceClient {
    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/emails")
    ResponseEntity<Response<EmailDTO>> sendEmail(@RequestBody SendEmailRequest request);
}
