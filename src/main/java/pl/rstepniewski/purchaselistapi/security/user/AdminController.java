package pl.rstepniewski.purchaselistapi.security.user;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Control Panel")
public class AdminController {
    private final UserRepository userRepository;

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> all = userRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/user")
    public ResponseEntity<List<User>> postUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PutMapping("/user")
    public ResponseEntity<List<User>> updateUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/user")
    @Hidden
    public ResponseEntity<List<User>> deleteAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

}