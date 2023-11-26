package pl.rstepniewski.purchaselistapi.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class Constants {
    private long tokenValidity;
    private String secureKeyAlgorithm;

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
}
