package pl.rstepniewski.purchaselistapi.security.passwordhash;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    public PasswordService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String rawToHash(String rawPassword ) {
       return passwordEncoder.encode(rawPassword);
    }

    public boolean isPasswordMatching(String rawPassword, String encodedPassword ) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
