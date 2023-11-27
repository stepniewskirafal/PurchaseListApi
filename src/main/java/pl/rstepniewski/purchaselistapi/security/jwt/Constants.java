package pl.rstepniewski.purchaselistapi.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class Constants {
    private long tokenValidity;
    private String secureKeyAlgorithm;
    private int secureKeyBitLength;
    private String apiSecretKey;

    public String getApiSecretKey() {
        return apiSecretKey;
    }

    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
    }

    public long getTokenValidity() {
        return tokenValidity;
    }

    public void setTokenValidity(long tokenValidity) {
        this.tokenValidity = tokenValidity;
    }

    public String getSecureKeyAlgorithm() {
        return secureKeyAlgorithm;
    }

    public void setSecureKeyAlgorithm(String secureKeyAlgorithm) {
        this.secureKeyAlgorithm = secureKeyAlgorithm;
    }

    public int getSecureKeyBitLength() {
        return secureKeyBitLength;
    }

    public void setSecureKeyBitLength(int secureKeyBitLength) {
        this.secureKeyBitLength = secureKeyBitLength;
    }
}
