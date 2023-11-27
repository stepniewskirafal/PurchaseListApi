package pl.rstepniewski.purchaselistapi.security.jwt;

import org.springframework.stereotype.Service;
import pl.rstepniewski.purchaselistapi.exception.WrongAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

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
            throw new WrongAlgorithmException("Wrong Algorithm type for SecretKey used in the config. Error stack: " + e.getMessage() );
        }
        final SecureRandom random = new SecureRandom();
        keyGen.init(constants.getSecureKeyBitLength(), random);
        return keyGen.generateKey();

    }

    public SecretKey getSecretKey() {
        String apiSecretKey = constants.getApiSecretKey();
        String encodedKey = Base64.getEncoder().encodeToString(apiSecretKey.getBytes());

        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, constants.getSecureKeyAlgorithm());
    }

}
