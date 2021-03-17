package ba.unsa.etf.nwt.userservice.seed;

import ba.unsa.etf.nwt.userservice.model.Token;
import ba.unsa.etf.nwt.userservice.model.User;
import ba.unsa.etf.nwt.userservice.repository.TokenRepository;
import ba.unsa.etf.nwt.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener
    public void seed(final ContextRefreshedEvent event) {
        seedUsersTable();
    }

    private void seedUsersTable() {
        if (userRepository.count() == 0) {
            User first = createUser("admin@projecthub.com", passwordEncoder.encode("password"), "Admin", "Admin");
            User second = createUser("info@projecthub.com", passwordEncoder.encode("password"), "Info", "Info");
            User third = createUser("suppport@projecthub.com", passwordEncoder.encode("password"), "Support", "Support");
            createToken(Token.TokenType.ACTIVATE_ACCOUNT, 15, first);
            createToken(Token.TokenType.ACTIVATE_ACCOUNT, 20, first);
            createToken(Token.TokenType.RESET_PASSWORD, 30, second);
            createToken(Token.TokenType.ACTIVATE_ACCOUNT, 20, third);
            createToken(Token.TokenType.ACTIVATE_ACCOUNT, 20, third);
            createToken(Token.TokenType.RESET_PASSWORD, 30, third);
        }
    }

    private User createUser(String email, String password, String first_name, String lastname) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(first_name);
        user.setLastName(lastname);
        user = userRepository.save(user);
        System.out.printf("Created user with UUID: %s\n", user.getId().toString());
        return user;
    }

    private Token createToken(Token.TokenType type, Integer duration, User user) {
        Token token = new Token();
        token.setType(type);
        token.setDuration(duration);
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setValid(true);
        token = tokenRepository.save(token);
        System.out.printf("Created token with UUID %s\n", token.getId().toString());
        return token;
    }
}
