package ba.unsa.etf.nwt.emailservice.emailservice.config;

import ba.unsa.etf.nwt.emailservice.emailservice.client.TaskServiceClient;
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
        if (bean instanceof TaskServiceClient)
            return Mockito.mock(TaskServiceClient.class);
        return bean;
    }
}
