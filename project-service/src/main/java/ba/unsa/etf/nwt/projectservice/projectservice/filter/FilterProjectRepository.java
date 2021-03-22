package ba.unsa.etf.nwt.projectservice.projectservice.filter;

import ba.unsa.etf.nwt.projectservice.projectservice.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterProjectRepository {
    Page<Project> findAllByFilter(ProjectFilter filter, Pageable pageable);
}
