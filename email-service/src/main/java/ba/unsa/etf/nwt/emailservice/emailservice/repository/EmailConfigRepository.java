package ba.unsa.etf.nwt.emailservice.emailservice.repository;

import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailConfigRepository extends PagingAndSortingRepository<EmailConfig, UUID> {
}
