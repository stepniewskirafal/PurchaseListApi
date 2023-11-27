package pl.rstepniewski.purchaselistapi;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.rstepniewski.purchaselistapi.security.AuthFilter;

@Configuration
public class WebConfig {

    private final AuthFilter authFilter;

    public WebConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> filterFilterRegistrationBean(){
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/api/categories/*");
        return registrationBean;
    }
}
