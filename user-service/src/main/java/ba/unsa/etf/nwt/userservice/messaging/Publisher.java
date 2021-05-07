package ba.unsa.etf.nwt.userservice.messaging;

import ba.unsa.etf.nwt.userservice.response.interfaces.Resource;

public interface Publisher<T extends Resource> {
    void send(T data);
}
