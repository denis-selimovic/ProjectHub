package ba.unsa.etf.nwt.emailservice.emailservice.messaging;

import ba.unsa.etf.nwt.emailservice.emailservice.response.interfaces.Resource;

public interface Publisher<T extends Resource> {
    void send(T data);
}
