package ba.unsa.etf.nwt.userservice.logging;

import ba.unsa.etf.nwt.systemevents.grpc.SystemEventRequest;
import com.google.protobuf.Timestamp;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

public class SystemEventFactory {

    private SystemEventFactory() {}

    public static SystemEventRequest createLogMessage(@NonNull HttpServletRequest request,
                                                      @NonNull HttpServletResponse response,
                                                      @NonNull Authentication auth,
                                                      @NonNull Class<?> bean,
                                                      @NonNull String responseBody) {
        Instant now = Instant.now();
        return SystemEventRequest.newBuilder()
                .setMethod(request.getMethod())
                .setResource(getResourceType(bean))
                .setAction(getActionType(request))
                .setRequestURL(request.getRequestURI())
                .setPrincipal(auth.getName())
                .setStatus(response.getStatus())
                .setTimestamp(Timestamp.newBuilder().setSeconds(now.getEpochSecond()).setNanos(now.getNano()).build())
                .setService("user-service")
                .setResponse(responseBody)
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
