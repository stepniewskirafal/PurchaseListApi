package pl.rstepniewski.purchaselistapi.security.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rstepniewski.purchaselistapi.security.user.User;
import pl.rstepniewski.purchaselistapi.security.user.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> all = userRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/users")
    public ResponseEntity<List<User>> postUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PutMapping("/users")
    public ResponseEntity<List<User>> updateUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/users")
    public ResponseEntity<List<User>> deleteAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

}
