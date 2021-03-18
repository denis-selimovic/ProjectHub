package ba.unsa.etf.nwt.userservice.service;

import ba.unsa.etf.nwt.userservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.userservice.model.User;
import ba.unsa.etf.nwt.userservice.repository.UserRepository;
import ba.unsa.etf.nwt.userservice.request.user.CreateUserRequest;
import ba.unsa.etf.nwt.userservice.request.user.ResetPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(CreateUserRequest request) {
        User user = createUserFromRequest(request);
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UnprocessableEntityException("Request body can not be processed");
        });
        return userRepository.save(user);
    }

    public User resetPassword(User user, ResetPasswordRequest request) {
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    private User createUserFromRequest(CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return user;
    }
}
