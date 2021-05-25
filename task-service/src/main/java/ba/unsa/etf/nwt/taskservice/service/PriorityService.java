package ba.unsa.etf.nwt.taskservice.service;

import ba.unsa.etf.nwt.taskservice.dto.PriorityDTO;
import ba.unsa.etf.nwt.taskservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.taskservice.model.Priority;
import ba.unsa.etf.nwt.taskservice.repository.PriorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PriorityService {
    private final PriorityRepository priorityRepository;

    public Priority findById(final UUID id) {
        return priorityRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Priority doesn't exist");
        });
    }

    public Page<Priority> findAll() {
        return priorityRepository.findAll(Pageable.unpaged());
    }
}
