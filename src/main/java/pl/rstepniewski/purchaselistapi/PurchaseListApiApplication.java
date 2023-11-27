package pl.rstepniewski.purchaselistapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PurchaseListApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurchaseListApiApplication.class, args);
    }

}
