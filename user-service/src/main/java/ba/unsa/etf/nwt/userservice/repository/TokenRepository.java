package ba.unsa.etf.nwt.userservice.repository;

import ba.unsa.etf.nwt.userservice.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenRepository extends CrudRepository<Token, UUID> {
}
