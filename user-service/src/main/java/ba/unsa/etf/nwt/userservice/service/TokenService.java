package ba.unsa.etf.nwt.userservice.service;

import ba.unsa.etf.nwt.userservice.model.Token;
import ba.unsa.etf.nwt.userservice.model.User;
import ba.unsa.etf.nwt.userservice.repository.TokenRepository;
import ba.unsa.etf.nwt.userservice.utility.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenUtils tokenUtils;
    private final TokenRepository tokenRepository;

    public Token generateActivationToken(User user) {
        Token token = generateToken(user, Token.TokenType.ACTIVATE_ACCOUNT, tokenUtils.getActivationTokenDuration());
        token = tokenRepository.save(token);
        return token;
    }

    private Token generateToken(User user, Token.TokenType type, Integer duration) {
        Token token = new Token();
        token.setType(type);
        token.setDuration(duration);
        token.setUser(user);
        token.setToken(tokenUtils.generateHashString());
        return token;
    }
}
