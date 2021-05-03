package ba.unsa.etf.nwt.notificationservice.logging;

import ba.unsa.etf.nwt.systemevents.grpc.SystemEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Qualifier("systemEventInterceptor")
@RequiredArgsConstructor
public class SystemEventInterceptor implements HandlerInterceptor {
    private final SystemEventService systemEventService;

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        HandlerMethod method = (HandlerMethod) handler;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SystemEventRequest req = SystemEventFactory.createLogMessage(request, response, auth, method.getBeanType());
        systemEventService.logSystemEvent(req);
    }
}