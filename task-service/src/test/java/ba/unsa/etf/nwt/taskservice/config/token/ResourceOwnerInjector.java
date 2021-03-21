package ba.unsa.etf.nwt.taskservice.config.token;

import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

@Profile("test")
@Component
public class ResourceOwnerInjector implements HandlerMethodArgumentResolver {

    public final static UUID id = UUID.fromString("8e980ea1-dcb2-4f19-972e-6c9cb7994ef3");
    public final static String email = "email@email.com";
    public final static String clientId = "test-id";
    public final static ArrayList<String> scopes = new ArrayList<>(Set.of("read", "write"));

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(ResourceOwner.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  @NonNull NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        return new ResourceOwner(id, email, clientId, scopes);
    }
}
