package ba.unsa.etf.nwt.projectservice.projectservice.service;

import ba.unsa.etf.nwt.projectservice.projectservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
}
