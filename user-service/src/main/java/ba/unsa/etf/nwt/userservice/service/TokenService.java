package ba.unsa.etf.nwt.userservice.service;

import ba.unsa.etf.nwt.userservice.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
}
