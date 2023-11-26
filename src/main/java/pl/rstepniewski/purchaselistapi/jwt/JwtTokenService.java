package pl.rstepniewski.purchaselistapi.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rstepniewski.purchaselistapi.domain.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.time.Instant;

@Service
public class JwtTokenService {

    private final Constants constants;
    private final SecretKeyService secretKeyService;


    @Autowired
    public JwtTokenService(Constants constants, SecretKeyService secretKeyService) {
        this.constants = constants;
        this.secretKeyService = secretKeyService;
    }

    public Map<String, String> generateJWToken(User user) {
        final Map<String, String> resultMap = new HashMap<>();
        resultMap.put("token", jwtsBuilder(user));
        return resultMap;
    }

    private String jwtsBuilder(User user) {
        final long timeStamp = Instant.now().toEpochMilli();

        final Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        final SecretKey key = secretKeyService.generateSecretKey();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(Instant.now()))
                .expiration(new Date(timeStamp + constants.getTokenValidity()))
                .signWith(key)
                .compact();
    }
}
