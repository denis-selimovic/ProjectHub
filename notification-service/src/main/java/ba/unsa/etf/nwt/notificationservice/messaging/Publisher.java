package ba.unsa.etf.nwt.notificationservice.messaging;

import ba.unsa.etf.nwt.notificationservice.response.interfaces.Resource;

public interface Publisher<T extends Resource> {
    void send(T data);
}
