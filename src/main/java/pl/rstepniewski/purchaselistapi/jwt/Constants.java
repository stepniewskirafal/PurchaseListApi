package pl.rstepniewski.purchaselistapi.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class Constants {
    private long tokenValidity;
    private String secureKeyAlgorithm;
    private int secureKeyBitLength;

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
