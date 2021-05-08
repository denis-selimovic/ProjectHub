package ba.unsa.etf.nwt.taskservice.utility;

import ba.unsa.etf.nwt.taskservice.dto.TaskNotificationDTO;
import ba.unsa.etf.nwt.taskservice.messaging.publishers.TaskNotificationPublisher;
import ba.unsa.etf.nwt.taskservice.model.Task;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class HibernateUtils extends EmptyInterceptor {

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if(entity.getClass().equals(Task.class)) {
            TaskNotificationPublisher publisher = ApplicationContextHolder.context.getBean(TaskNotificationPublisher.class);
            TaskNotificationDTO data = publisher.createNotification(previousState, currentState, propertyNames);
            publisher.send(data);
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}
