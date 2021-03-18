package ba.unsa.etf.nwt.userservice.controller;

import ba.unsa.etf.nwt.userservice.dto.GrantTokenDTO;
import ba.unsa.etf.nwt.userservice.exception.ExceptionsHandler;
import ba.unsa.etf.nwt.userservice.request.GrantTokenRequest;
import ba.unsa.etf.nwt.userservice.response.base.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping(value = "/oauth/token")
public class TokenController extends TokenEndpoint {

    @PostMapping
    public ResponseEntity<Response> postAccessToken(
            Principal principal,
            @Valid @RequestBody GrantTokenRequest request
    ) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oauthResponse = super.postAccessToken(principal, request.toForm()).getBody();
        return ResponseEntity.ok(new Response(new GrantTokenDTO(oauthResponse)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodNotValid(MethodArgumentNotValidException ex) {
        return ExceptionsHandler.handleMethodArgumentNotValid(ex);
    }
}
