package ba.unsa.etf.nwt.userservice.logging;

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
@Qualifier("loggerInterceptor")
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        HandlerMethod method = (HandlerMethod) handler;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LogMessage logMessage = LogMessageFactory.createLogMessage(request, response, auth, method.getBeanType());
        System.out.println(logMessage.toString());
    }
}
