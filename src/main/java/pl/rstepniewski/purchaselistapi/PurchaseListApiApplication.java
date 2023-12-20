package pl.rstepniewski.purchaselistapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.rstepniewski.purchaselistapi.security.auth.AuthenticationService;
import pl.rstepniewski.purchaselistapi.security.auth.RegisterRequest;

import static pl.rstepniewski.purchaselistapi.security.roleperms.Role.ADMIN;
import static pl.rstepniewski.purchaselistapi.security.roleperms.Role.MANAGER;

@SpringBootApplication
public class PurchaseListApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PurchaseListApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ){
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@email.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: "+ service.register(admin).getAccessToken());

            var manager = RegisterRequest.builder()
                    .firstname("Manager")
                    .lastname("Manager")
                    .email("manager@email.com")
                    .password("password")
                    .role( MANAGER )
                    .build();
            System.out.println("Manager token: "+ service.register(manager).getAccessToken());
        };
    }
}
