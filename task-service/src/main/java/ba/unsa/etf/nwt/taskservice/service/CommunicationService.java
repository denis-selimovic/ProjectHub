package ba.unsa.etf.nwt.taskservice.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommunicationService {
    public void checkIfCollaborator(final UUID userId, final UUID projectId) {
        //TODO
        //if not throw 403
    }

    public void checkIfProjectExists(final UUID projectId) {
        //TODO
        //if not throw 422
    }
}
