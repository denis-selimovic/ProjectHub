package ba.unsa.etf.nwt.taskservice.service;

import ba.unsa.etf.nwt.taskservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.taskservice.model.Status;
import ba.unsa.etf.nwt.taskservice.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public Status findById(final UUID id) {
        return statusRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Request body can not be processed");
        });
    }

    public Status findByStatusType(final Status.StatusType type) {
        return statusRepository.findByStatus(type).orElseThrow(() -> {
            throw new NotFoundException("Request body can not be processed");
        });
    }
}
