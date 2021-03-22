package ba.unsa.etf.nwt.userservice.config;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public interface OAuth2ClientDetailsRepository extends CrudRepository<OAuth2ClientDetails, String> {
}
