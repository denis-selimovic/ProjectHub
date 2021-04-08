package ba.unsa.etf.nwt.systemevents.repository;

import ba.unsa.etf.nwt.systemevents.model.SystemEvent;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface SystemEventRepository extends PagingAndSortingRepository<SystemEvent, UUID> {
}
