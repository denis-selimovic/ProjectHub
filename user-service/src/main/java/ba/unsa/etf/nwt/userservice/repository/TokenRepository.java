package ba.unsa.etf.nwt.userservice.repository;

import ba.unsa.etf.nwt.userservice.model.Token;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends PagingAndSortingRepository<Token, UUID> {
    Optional<Token> findTokenByTokenAndType(String token, Token.TokenType type);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tokens t SET valid = FALSE WHERE t.user_id = :userId AND t.type = :type", nativeQuery = true)
    void invalidateTokenByType(UUID userId, String type);
}
