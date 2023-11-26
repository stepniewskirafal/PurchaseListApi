package pl.rstepniewski.purchaselistapi.jwt;

import org.springframework.stereotype.Service;
import pl.rstepniewski.purchaselistapi.exception.WrongAlgorithmException;

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
            throw new WrongAlgorithmException("Wrong Algorithm type for SecretKey used in the config. Error stack: " + e.getMessage() );
        }
        final SecureRandom random = new SecureRandom();
        keyGen.init(constants.getSecureKeyBitLength(), random);
        return keyGen.generateKey();

    }
}
