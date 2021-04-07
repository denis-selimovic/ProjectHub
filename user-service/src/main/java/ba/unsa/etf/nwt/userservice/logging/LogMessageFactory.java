package ba.unsa.etf.nwt.userservice.logging;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LogMessageFactory {

    private LogMessageFactory () {}

    public static LogMessage createLogMessage(@NonNull HttpServletRequest request,
                                              @NonNull HttpServletResponse response,
                                              @NonNull Authentication auth,
                                              @NonNull Class<?> bean) {
        return LogMessage.builder()
                .method(request.getMethod())
                .resource(getResourceType(bean))
                .action(getActionType(request))
                .requestURL(request.getRequestURI())
                .principal(auth.getName())
                .status(response.getStatus())
                .build();
    }

    private static String getResourceType(Class<?> bean) {
        return switch (bean.getSimpleName()) {
            case "UsersController" -> "USER";
            case "TokenController" -> "TOKEN";
            default -> "UNKNOWN RESOURCE";
        };
    }

    private static String getActionType(HttpServletRequest request) {
        return switch (request.getMethod()) {
            case "POST" -> "CREATE";
            case "PUT", "PATCH" -> "EDIT";
            case "GET" -> "GET";
            case "DELETE" -> "DELETE";
            default -> "UNKNOWN ACTION";
        };
    }
}
