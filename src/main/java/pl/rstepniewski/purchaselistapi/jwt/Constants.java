package pl.rstepniewski.purchaselistapi.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class Constants {
    private String apiSecretKey;
    private long tokenValidity;

    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
    }

    public void setTokenValidity(long tokenValidity) {
        this.tokenValidity = tokenValidity;
    }

    public String getApiSecretKey() {
        return apiSecretKey;
    }

    public long getTokenValidity() {
        return tokenValidity;
    }
}
