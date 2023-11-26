package pl.rstepniewski.purchaselistapi.jwt;

import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class SecretKeyService {

    private final Constants constants;

    public SecretKeyService(Constants constants) {
        this.constants = constants;
    }

    public SecretKey generateSecretKey() {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance(constants.getSecureKeyAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecureRandom random = new SecureRandom();
        keyGen.init(512, random);  // 512-bit key for HS512
        return keyGen.generateKey();

    }
}
