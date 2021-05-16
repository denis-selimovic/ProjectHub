package ba.unsa.etf.nwt.taskservice.messaging;

import ba.unsa.etf.nwt.taskservice.response.interfaces.Resource;

public interface Publisher<T extends Resource> {
    void send(T data);
}
