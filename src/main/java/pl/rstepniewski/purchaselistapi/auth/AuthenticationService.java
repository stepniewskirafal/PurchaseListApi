package pl.rstepniewski.purchaselistapi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
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

import java.io.IOException;

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
        var jwtRefreshToken  = jwtService.generateRefreshToken(user);

        revokeAllUserToken(savedUser);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(jwtRefreshToken)
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
        var jwtRefreshToken  = jwtService.generateRefreshToken(user);

        revokeAllUserToken(user);
        saveUserToken(user,jwtToken);

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(jwtRefreshToken)
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

    public AuthenticationResponse refreshToken(
            HttpServletRequest request) {
        AuthenticationResponse authenticationResponse = null;
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return authenticationResponse;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if( userEmail != null){
            var userDetails = this.userRepository.findUserByEmail(userEmail).orElseThrow();

            if(jwtService.isTokenValid(refreshToken, userDetails)){
                var accessToken = jwtService.generateToken(userDetails);
                revokeAllUserToken(userDetails);
                saveUserToken(userDetails, accessToken);
                authenticationResponse = AuthenticationResponse
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        return authenticationResponse;
    }
}
