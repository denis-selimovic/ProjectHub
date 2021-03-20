package ba.unsa.etf.nwt.taskservice.service;

import ba.unsa.etf.nwt.taskservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.taskservice.model.Type;
import ba.unsa.etf.nwt.taskservice.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeRepository typeRepository;

    public Type findById(final UUID id) {
        return typeRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Request body can not be processed");
        });
    }
}
