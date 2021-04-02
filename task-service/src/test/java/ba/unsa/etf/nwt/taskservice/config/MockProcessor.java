package ba.unsa.etf.nwt.taskservice.config;

import ba.unsa.etf.nwt.taskservice.client.ProjectServiceClient;
import ba.unsa.etf.nwt.taskservice.client.service.ProjectService;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class MockProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ProjectServiceClient)
            return Mockito.mock(ProjectServiceClient.class);
        if (bean instanceof ProjectService)
            return Mockito.mock(ProjectService.class);
        return bean;
    }
}
