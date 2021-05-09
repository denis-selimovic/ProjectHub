package ba.unsa.etf.nwt.taskservice.logging;

import ba.unsa.etf.nwt.systemevents.grpc.SystemEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Qualifier("systemEventInterceptor")
@RequiredArgsConstructor
public class SystemEventInterceptor implements HandlerInterceptor, ResponseBodyAdvice<Object> {
    private final SystemEventService systemEventService;
    private Object cachedResponseBody;

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        HandlerMethod method = (HandlerMethod) handler;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SystemEventRequest req = SystemEventFactory.createLogMessage(request, response, auth, method.getBeanType(), String.valueOf(cachedResponseBody));
        systemEventService.logSystemEvent(req);
    }

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  @NonNull MethodParameter methodParameter,
                                  @NonNull MediaType mediaType,
                                  @NonNull Class aClass,
                                  @NonNull ServerHttpRequest serverHttpRequest,
                                  @NonNull ServerHttpResponse serverHttpResponse) {
        cachedResponseBody = o;
        return o;
    }
}
