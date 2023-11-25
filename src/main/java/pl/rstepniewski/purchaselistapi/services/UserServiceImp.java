package pl.rstepniewski.purchaselistapi.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.rstepniewski.purchaselistapi.crypto.PasswordService;
import pl.rstepniewski.purchaselistapi.domain.User;
import pl.rstepniewski.purchaselistapi.exception.EtAuthException;
import pl.rstepniewski.purchaselistapi.repository.UserRepository;

import java.util.Map;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImp implements UserService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public UserServiceImp(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public User validateUser(User user) throws EtAuthException {
        validateEmail(user.getEmail());

        User userFound = userRepository.findUserByEmail(user.getEmail())
                .orElseThrow(() -> new EtAuthException("User not found"));

        if (!passwordService.isPasswordMatching(user.getPassword(), userFound.getPassword())) {
            throw new EtAuthException("Wrong Password");
        }

        return userFound;
    }

    @Override
    public User registerUser(User user) throws EtAuthException {
        validateEmail(user.getEmail());

        if ( userRepository.existsUserByEmail(user.getEmail()) ) {
            throw new EtAuthException("Email already in use");
        }

        user.setPassword(passwordService.rawToHash(user.getPassword()));
        return userRepository.save(user);
    }

    private void validateEmail(String email) throws EtAuthException {
        if (email == null) {
            throw new EtAuthException("Email can't be null");
        }

        if (!EMAIL_PATTERN.matcher(email.toLowerCase()).matches()) {
            throw new EtAuthException("Invalid email format");
        }
    }

    public User processUserRequestBody(Map<String, Object> userMap) {
        String firstName = (String) userMap.get("firstName");
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        return new User(firstName, lastName, email.toLowerCase(), password);
    }
}
