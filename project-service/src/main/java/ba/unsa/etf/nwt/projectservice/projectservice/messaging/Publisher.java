package ba.unsa.etf.nwt.projectservice.projectservice.messaging;

import ba.unsa.etf.nwt.projectservice.projectservice.response.interfaces.Resource;

public interface Publisher<T extends Resource> {
    void send(T data);
}
