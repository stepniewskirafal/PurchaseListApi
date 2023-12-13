package pl.rstepniewski.purchaselistapi.auth;

import pl.rstepniewski.purchaselistapi.config.JwtService;
import pl.rstepniewski.purchaselistapi.model.token.Token;
import pl.rstepniewski.purchaselistapi.model.token.TokenRepository;
import pl.rstepniewski.purchaselistapi.model.token.TokenType;
import pl.rstepniewski.purchaselistapi.model.user.Role;
import pl.rstepniewski.purchaselistapi.model.user.User;
import pl.rstepniewski.purchaselistapi.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        revokeAllUserToken(savedUser);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository
                .findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find a user email: "+ request.getEmail()));

        var jwtToken = jwtService.generateToken(user);

        revokeAllUserToken(user);
        saveUserToken(user,jwtToken);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User savedUser, String jwtToken) {
        var token = Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserToken(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if( validUserTokens.isEmpty()){
            return;
        }
        for (Token token : validUserTokens ) {
            token.setExpired(true);
            token.setRevoked(true);
        }

        tokenRepository.saveAll(validUserTokens);
    }
}
