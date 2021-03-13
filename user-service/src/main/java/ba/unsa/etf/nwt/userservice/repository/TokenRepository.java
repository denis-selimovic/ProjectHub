package ba.unsa.etf.nwt.userservice.repository;

import ba.unsa.etf.nwt.userservice.model.Token;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenRepository extends PagingAndSortingRepository<Token, UUID> {
}
