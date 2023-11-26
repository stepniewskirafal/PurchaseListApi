package pl.rstepniewski.purchaselistapi.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rstepniewski.purchaselistapi.domain.User;
import static io.jsonwebtoken.JwsHeader.KEY_ID;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.time.Instant;

@Service
public class JwtTokenService {

    private final Constants constants;

    @Autowired
    public JwtTokenService(Constants constants) {
        this.constants = constants;
    }

    public Map<String, String> generateJWToken(User user) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("token", jwtsBuilder(user));
        return resultMap;
    }

    private String jwtsBuilder(User user) {
        long timeStamp = Instant.now().toEpochMilli();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        SecretKey key = Keys.hmacShaKeyFor(constants.getApiSecretKey().getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(Instant.now()))
                .expiration(new Date(timeStamp + constants.getTokenValidity()))
                .signWith(key)
                .compact();
    }
}
